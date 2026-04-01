package com.daker.service;

import com.daker.domain.dto.request.ArticleRequestDTO;
import com.daker.domain.dto.request.TeamRequestDTO;
import com.daker.domain.dto.response.ArticleResponseDTO;
import com.daker.domain.dto.response.TeamResponseDTO;
import com.daker.domain.entity.*;
import com.daker.domain.entity.mapping.TargetPosition;
import com.daker.domain.entity.mapping.TeamHackathon;
import com.daker.domain.entity.mapping.UserTeam;
import com.daker.repository.*;
import com.daker.util.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

        TeamHackathon teamHackathon = TeamHackathon.builder()
                .team(team).hackathon(hackathon).build();
        teamHackathonRepository.save(teamHackathon);
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

        User target = userRepository.findById(userId).orElseThrow(() -> new ApiException(NOT_FOUND_404));
        UserTeam tRow = userTeamRepository.findByUserAndTeam(target, team).orElseThrow(() -> new ApiException(BAD_REQUEST));
        tRow.setPosition(positionRepository.findById(request.getPositionId()).get());

        userTeamRepository.save(tRow);

        // 팀 멤버의 역할을 수정한 경우, 공고글의 경우도 바뀌어야 하나?
    }



    // 공고글 관련
    public ArticleResponseDTO.ArticleIdDTO createArticle(long userId, long teamId, ArticleRequestDTO.CreateArticleDTO request) {
        User user = userRepository.findById(userId).get();
        Team team = teamRepository.findById(teamId).get();

        Article article = Article.builder()
                        .team(team)
                        .title(request.getTitle())
                        .content(request.getContent())
                        .isOpen(true)
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
}