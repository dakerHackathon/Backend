package com.daker.service;

import com.daker.domain.dto.request.ArticleRequestDTO;
import com.daker.domain.dto.request.TeamRequestDTO;
import com.daker.domain.dto.response.ArticleResponseDTO;
import com.daker.domain.dto.response.TeamResponseDTO;
import com.daker.domain.entity.*;
import com.daker.domain.entity.mapping.TargetPosition;
import com.daker.domain.entity.mapping.TeamEnter;
import com.daker.domain.entity.mapping.TeamHackathon;
import com.daker.domain.entity.mapping.UserTeam;
import com.daker.repository.*;
import com.daker.util.ApiResponse;
import com.daker.util.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

import static com.daker.util.code.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final UserTeamRepository userTeamRepository;
    private final HackathonRepository hackathonRepository;
    private final TeamHackathonRepository teamHackathonRepository;
    private final PositionRepository positionRepository;
    private final TargetPositionRepository targetPositionRepository;
    private final ArticleRepository articleRepository;
    private final TeamEnterRepository teamEnterRepository;

    public TeamResponseDTO.GetTeamDetailDTO getTeamDetail(long userId, long teamId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(USER_NOT_FOUND_404));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ApiException(TEAM_NOT_FOUND_404));
        userTeamRepository.findByUserAndTeam(user, team).orElseThrow(() -> new ApiException(BAD_REQUEST));

        return TeamResponseDTO.GetTeamDetailDTO.builder()
                .team(TeamResponseDTO.TeamDetailDTO.builder()
                        .teamId(team.getId())
                        .teamName(team.getName())
                        .description(team.getDescription()).build())
                .member(userTeamRepository.findAllUsersByTeam(team).stream()
                        .map((u) -> TeamResponseDTO.TeamMemberDTO.builder()
                                .userId(u.getId())
                                .nickName(u.getNickname())
                                .position(userTeamRepository.getPositionByUserANDTeam(u, team).getId())
                                .isLeader(userTeamRepository.findByUserAndTeam(u, team).get().getLeader()).build())
                        .toList())
                .hackathon(teamHackathonRepository.findAllByTeam(team).stream()
                        .filter((th) -> th.getHackathon().getStartAt().isBefore(LocalDateTime.now()) && th.getHackathon().getEndAt().isAfter(LocalDateTime.now()))
                        .map((th) -> TeamResponseDTO.HackathonDTO.builder()
                                .hackathonId(th.getHackathon().getId())
                                .hackathonName(th.getHackathon().getTitle())
                                .startAt(th.getHackathon().getStartAt().toString())
                                .endAt(th.getHackathon().getEndAt().toString()).build())
                        .findFirst().orElse(null))
                .build();
    }

    public TeamResponseDTO.TeamInfoListDTO getOwnTeams(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(NOT_FOUND_404));

        return TeamResponseDTO.TeamInfoListDTO.builder().teams(
                userTeamRepository.findOwnTeamsByUser(user).stream().map((team) -> TeamResponseDTO.TeamInfoDTO.builder()
                    .teamId(team.getId())
                    .teamName(team.getName()).build()
                ).toList()
        ).build();
    }

    public TeamResponseDTO.TeamIdDTO createTeam(long userId, TeamRequestDTO.CreateTeamDTO request) {
        User user = userRepository.findById(userId).get();

        Team team = Team.builder()
                .name(request.getName())
                .description(request.getDescription()).build();
        team = teamRepository.save(team);

        UserTeam userTeam = UserTeam.builder()
                .team(team)
                .user(user)
                .position(positionRepository.findById(request.getRole()).get())
                .leader(true).build();
        userTeamRepository.save(userTeam);

        return TeamResponseDTO.TeamIdDTO.builder()
                .teamId(team.getId()).build();
    }

    public void registerHackathon(long userId, long teamId, long hackathonId) {
        User user = userRepository.findById(userId).get();
        Team team = teamRepository.findById(teamId).get();
        Hackathon hackathon = hackathonRepository.findById(hackathonId).get();

        if(teamHackathonRepository.findByTeamAndHackathon(team, hackathon).isPresent()) throw new ApiException(BAD_REQUEST);
        else {
            System.out.println("왜 여길 들어오냐");
                    
            teamHackathonRepository.save(TeamHackathon.builder()
                    .team(team).hackathon(hackathon).build());
        }
    }

    public void deleteTeam(long userId, long teamId) {
        User user = userRepository.findById(userId).get();
        Team team = teamRepository.findById(teamId).get();

        teamRepository.delete(team);
    }

    public void leaveTeam(long userId, long teamId) {
        User user = userRepository.findById(userId).get();
        Team team = teamRepository.findById(teamId).get();

        UserTeam userTeam = userTeamRepository.findByUserAndTeam(user, team).orElseThrow(() -> new ApiException(BAD_REQUEST));
        userTeamRepository.delete(userTeam);
    }

    public void expellUser(TeamRequestDTO.ExpellUserDTO request) {
        leaveTeam(request.getUserId(), request.getTeamId());
    }

    public TeamResponseDTO.TeamIdDTO editTeam(long userId, long teamId, TeamRequestDTO.EditTeamInfoDTO request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(NOT_FOUND_404));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ApiException(NOT_FOUND_404));

        UserTeam ut = userTeamRepository.findByUserAndTeam(user, team).orElseThrow(() -> new ApiException(BAD_REQUEST));
        if(!ut.getLeader()) throw new ApiException(UNAUTHORIZED_401);

        team.setName(request.getName());
        team.setDescription(request.getDescription());
        team = teamRepository.save(team);

        return TeamResponseDTO.TeamIdDTO.builder().teamId(team.getId()).build();
    }

    public void changeRole(long userId, long teamId, TeamRequestDTO.MemberEditDTO request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(NOT_FOUND_404));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ApiException(NOT_FOUND_404));

        UserTeam ut = userTeamRepository.findByUserAndTeam(user, team).orElseThrow(() -> new ApiException(BAD_REQUEST));
        if(!ut.getLeader()) throw new ApiException(UNAUTHORIZED_401);

        User target = userRepository.findById(request.getUserId()).orElseThrow(() -> new ApiException(NOT_FOUND_404));
        UserTeam tRow = userTeamRepository.findByUserAndTeam(target, team).orElseThrow(() -> new ApiException(BAD_REQUEST));
        tRow.setPosition(positionRepository.findById(request.getPosition()).orElseThrow(() -> new ApiException(BAD_POSITION_REQUEST)));

        userTeamRepository.save(tRow);

        // 팀 멤버의 역할을 수정한 경우, 공고글의 경우도 바뀌어야 하나?
    }



    // 공고글 관련
    public ArticleResponseDTO.ArticleIdDTO createArticle(long userId, long teamId, ArticleRequestDTO.CreateArticleDTO request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(USER_NOT_FOUND_404));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ApiException(TEAM_NOT_FOUND_404));

        Article article = Article.builder()
                        .team(team)
                        .title(request.getTitle())
                        .content(request.getContent())
                        .isOpen(true)
                        .writer(user)
                        .createdAt(LocalDateTime.now())
                        .contact(request.getContact()).build();
        article = articleRepository.save(article);

        for(ArticleRequestDTO.LookingForDTO data : request.getLookingFor()) {
            TargetPosition targets = TargetPosition.builder()
                    .position(positionRepository.findById(data.getPositionId()).get())
                    .count(data.getHeadCount())
                    .article(article).build();
            targetPositionRepository.save(targets);
        }

        return ArticleResponseDTO.ArticleIdDTO.builder()
                .articleId(article.getId()).build();
    }

    public TeamResponseDTO.PositionsDTO getPositions() {
        return TeamResponseDTO.PositionsDTO.builder()
                .positions(
                        positionRepository.findAll().stream()
                                .map(position -> TeamResponseDTO.PositionDTO.builder()
                                        .id(position.getId())
                                        .name(position.getName())
                                        .abb(position.getAbbreviation()).build()
                                ).toList())
                .build();
    }

    @Transactional(readOnly = true)
    public ArticleResponseDTO.GetRecruitDTO getArticles(String open, String position) {
        boolean isOpen = open == null || open.isBlank() || open.equals("1") || open.equalsIgnoreCase("true");
        Integer positionId = (position == null || position.isBlank()) ? null : Integer.parseInt(position);

        List<Article> articles = articleRepository.findAllByIsOpenWithTeam(isOpen);

        if (positionId != null) {
            articles = articles.stream()
                    .filter((a) -> {
                        List<TargetPosition> targets = a.getTargetPositions();

                        boolean ans = false;
                        for(TargetPosition target : targets) {
                            if(target.getPosition().getId() == positionId) ans = true;
                        }
                        return ans;
                    }).toList();
        }

        List<ArticleResponseDTO.RecruitDTO> result = articles.stream()
                .map(article -> {
                    Team team = article.getTeam();

                    List<ArticleResponseDTO.PositionDTO> articlePositions = article.getTargetPositions().stream()
                            .map(tp -> ArticleResponseDTO.PositionDTO.builder()
                                    .position(tp.getPosition().getId())
                                    .headCount(tp.getCount())
                                    .build())
                            .toList();

                    List<Integer> teamPositions = userTeamRepository.findAllByTeam(team).stream()
                            .map(userTeam -> userTeam.getPosition().getId())
                            .distinct()
                            .toList();

                    TeamHackathon teamHackathon = teamHackathonRepository.findFirstByTeam(team)
                            .orElse(null);

                    ArticleResponseDTO.ConnectedHackathonDTO hackathonInfo = null;
                    if (teamHackathon != null && teamHackathon.getHackathon() != null) {
                        hackathonInfo = ArticleResponseDTO.ConnectedHackathonDTO.builder()
                                .hackathonId(teamHackathon.getHackathon().getId())
                                .hackathonTitle(teamHackathon.getHackathon().getTitle())
                                .build();
                    }

                    return ArticleResponseDTO.RecruitDTO.builder()
                            .article(ArticleResponseDTO.ArticleInfoDTO.builder()
                                    .id(article.getId())
                                    .title(article.getTitle())
                                    .content(article.getContent())
                                    .positions(articlePositions)
                                    .isOpen(article.getIsOpen())
                                    .writer(article.getWriter().getId())
                                    .createdAt(article.getCreatedAt().toString())
                                    .build())
                            .team(ArticleResponseDTO.CurrentTeamDTO.builder()
                                    .id(team.getId())
                                    .name(team.getName())
                                    .positions(teamPositions)
                                    .hackathon(hackathonInfo)
                                    .build())
                            .build();
                })
                .toList();

        return ArticleResponseDTO.GetRecruitDTO.builder()
                .articles(result)
                .build();
    }

    public ArticleResponseDTO.GetRecruitDTO searchRecruit(Long userId, String filter, String query) {
        String keyword = (query == null) ? "" : query.trim();

        List<Article> articles;

        if (keyword.isBlank()) {
            articles = articleRepository.findAllWithTeam();
        } else {
            switch (filter) {
                case "title" -> articles = articleRepository.searchByTitleOrContent(keyword);
                case "hack" -> articles = articleRepository.searchByHackathonTitle(keyword);
                default -> throw new ApiException(BAD_REQUEST);
            }
        }

        List<ArticleResponseDTO.RecruitDTO> result = articles.stream()
                .map(article -> {
                    Team team = article.getTeam();

                    List<ArticleResponseDTO.PositionDTO> articlePositions = article.getTargetPositions().stream()
                            .map(tp -> ArticleResponseDTO.PositionDTO.builder()
                                    .position(tp.getPosition().getId())
                                    .headCount(tp.getCount())
                                    .build())
                            .toList();

                    List<Integer> teamPositions = userTeamRepository.findAllByTeam(team).stream()
                            .map(userTeam -> userTeam.getPosition().getId())
                            .distinct()
                            .toList();

                    TeamHackathon teamHackathon = teamHackathonRepository.findFirstByTeam(team)
                            .orElse(null);

                    ArticleResponseDTO.ConnectedHackathonDTO hackathonInfo = null;
                    if (teamHackathon != null && teamHackathon.getHackathon() != null) {
                        hackathonInfo = ArticleResponseDTO.ConnectedHackathonDTO.builder()
                                .hackathonId(teamHackathon.getHackathon().getId())
                                .hackathonTitle(teamHackathon.getHackathon().getTitle())
                                .build();
                    }

                    return ArticleResponseDTO.RecruitDTO.builder()
                            .article(ArticleResponseDTO.ArticleInfoDTO.builder()
                                    .id(article.getId())
                                    .title(article.getTitle())
                                    .content(article.getContent())
                                    .positions(articlePositions)
                                    .isOpen(article.getIsOpen())
                                    .writer(article.getWriter().getId())
                                    .createdAt(article.getCreatedAt().toString())
                                    .build())
                            .team(ArticleResponseDTO.CurrentTeamDTO.builder()
                                    .id(team.getId())
                                    .name(team.getName())
                                    .positions(teamPositions)
                                    .hackathon(hackathonInfo)
                                    .build())
                            .build();
                })
                .toList();

        return ArticleResponseDTO.GetRecruitDTO.builder()
                .articles(result)
                .build();
    }

    // 모집글 수정
    public ArticleResponseDTO.ArticleIdDTO updateArticle(Long userId, Long articleId, ArticleRequestDTO.CreateArticleDTO request) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ApiException(NOT_FOUND_404));

        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setContact(request.getContact());
        article = articleRepository.save(article);

        List<TargetPosition> targets = targetPositionRepository.findAllByArticle(article);
        targetPositionRepository.deleteAll(targets);

        for(ArticleRequestDTO.LookingForDTO row : request.getLookingFor()) {
            TargetPosition temp = TargetPosition.builder()
                    .article(article)
                    .position(positionRepository.findById(row.getPositionId()).get())
                    .count(row.getHeadCount()).build();

            targetPositionRepository.save(temp);
        }

        return ArticleResponseDTO.ArticleIdDTO.builder()
                .articleId(article.getId()).build();
    }

    // 모집글 삭제
    public void deleteArticle(Long userId, ArticleRequestDTO.ArticleIdDTO request){
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(USER_NOT_FOUND_404));
        Article article = articleRepository.findById(request.getArticleId()).orElseThrow(() -> new ApiException(NOT_FOUND_404));
        if(article.getWriter().getId() != user.getId()) throw new ApiException(UNAUTHORIZED_401);

        articleRepository.delete(article);
    }

    // 모집글 마감
    public ArticleResponseDTO.ArticleIdDTO deadlineArticle(Long userId, ArticleRequestDTO.ArticleIdDTO request){
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(USER_NOT_FOUND_404));
        Article article = articleRepository.findById(request.getArticleId()).orElseThrow(() -> new ApiException(NOT_FOUND_404));
        if(article.getWriter().getId() != user.getId()) throw new ApiException(UNAUTHORIZED_401);

        article.setIsOpen(false);
        articleRepository.save(article);

        return ArticleResponseDTO.ArticleIdDTO.builder()
                .articleId(article.getId()).build();
    }



    // 팀 초대, 참가
    public void invite(long userId, long teamId, TeamRequestDTO.InviteMemberDTO request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(USER_NOT_FOUND_404));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ApiException(TEAM_NOT_FOUND_404));
        UserTeam validation = userTeamRepository.findByUserAndTeam(user, team).orElseThrow(() -> new ApiException(BAD_REQUEST));
        if(!validation.getLeader()) throw new ApiException(FORBIDDEN_403);

        TeamEnter teamEnter = TeamEnter.builder()
                .type(2)
                .content(request.getContent())
                .team(team)
                .sender(user)
                .receiver(userRepository.findById(request.getUserId()).orElseThrow(() -> new ApiException(USER_NOT_FOUND_404)))
                .position(positionRepository.findById(request.getPosition()).orElseThrow(() -> new ApiException(BAD_REQUEST)))
                .created_at(LocalDateTime.now()).build();

        teamEnterRepository.save(teamEnter);
    }

    public void join(long userId, TeamRequestDTO.JoinDTO request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(USER_NOT_FOUND_404));
        Team team = teamRepository.findById(request.getTeamId()).orElseThrow(() -> new ApiException(TEAM_NOT_FOUND_404));
        if (userTeamRepository.findAllUsersByTeam(team).size() >= 5) throw new ApiException(TEAM_MEMBER_EXCEEDED);

        TeamEnter teamEnter = TeamEnter.builder()
                .type(1)
                .content(request.getContent())
                .sender(user)
                .team(team)
                .receiver(userTeamRepository.findLeaderByTeam(team))
                .position(positionRepository.findById(request.getPosition()).orElseThrow(() -> new ApiException(BAD_REQUEST)))
                .created_at(LocalDateTime.now()).build();

        teamEnterRepository.save(teamEnter);
    }

    public void answer(long userId, TeamRequestDTO.AnswerDTO request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(USER_NOT_FOUND_404));
        TeamEnter teamEnter = teamEnterRepository.findById(request.getInvitationId()).orElseThrow(() -> new ApiException(NOT_FOUND_404));
        if(teamEnter.getReceiver() != user) throw new ApiException(FORBIDDEN_403);

        teamEnterRepository.delete(teamEnter);

        switch (teamEnter.getType()) {
            case 1 -> AnswerJoin(teamEnter.getSender(), teamEnter, request.isAccept());
            case 2 -> AnswerInvite(user, teamEnter, request.isAccept());
            default -> throw new ApiException(BAD_REQUEST);
        }
    }

    public void AnswerJoin(User user, TeamEnter te, boolean accept) {
        Team team = te.getTeam();

        if(!accept) return;
        if(userTeamRepository.findAllUsersByTeam(team).size() >= 5) throw new ApiException(TEAM_MEMBER_EXCEEDED);

        UserTeam ut = UserTeam.builder()
                .user(user)
                .team(team)
                .leader(false)
                .position(te.getPosition()).build();
        userTeamRepository.save(ut);
    }

    public void AnswerInvite(User user, TeamEnter te, boolean accept) {
        Team team = te.getTeam();

        if(!accept) return;
        if(userTeamRepository.findAllUsersByTeam(team).size() >= 5) throw new ApiException(TEAM_MEMBER_EXCEEDED);

        UserTeam ut = UserTeam.builder()
                .user(user)
                .team(team)
                .leader(false)
                .position(te.getPosition()).build();
        userTeamRepository.save(ut);
    }
}