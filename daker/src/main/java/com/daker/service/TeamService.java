package com.daker.service;


import com.daker.domain.dto.request.ArticleRequestDTO;
import com.daker.domain.dto.request.TeamRequestDTO;
import com.daker.domain.dto.response.ArticleResponseDTO;
import com.daker.domain.dto.response.HackathonResponseDTO;
import com.daker.domain.dto.response.TeamResponseDTO;
import com.daker.domain.entity.*;
import com.daker.repository.*;
import com.daker.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

    public TeamResponseDTO.TeamIdDTO createTeam(long userId, TeamRequestDTO.CreateTeamDTO request) {
        User user = userRepository.findById(userId).get();

        Team team = Team.builder()
                .name(request.getName())
                .description(request.getDescription()).build();
        team = teamRepository.save(team);

        UserTeam userTeam = UserTeam.builder()
                .team(team)
                .user(user)
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

        UserTeam userTeam = userTeamRepository.findByUserAndTeam(user, team);
        userTeamRepository.delete(userTeam);
    }

    public void expellUser(TeamRequestDTO.expellUserDTO request) {
        leaveTeam(request.getUserId(), request.getTeamId());
    }



    // 공고글 관련
    public ArticleResponseDTO.ArticleIdDTO createArticle(long userId, long teamId, ArticleRequestDTO.CreateArticleDTO request) {
        User user = userRepository.findById(userId).get();
        Team team = teamRepository.findById(teamId).get();

        Article article = new Article();
        for(ArticleRequestDTO.lookingForDTO data : request.getLookingFor()) {
            TargetPosition targets = TargetPosition.builder()
                    .position(positionRepository.findById(data.getPositionId()).get())
                    .count(data.getHeadCount())
                    .article(article).build();
            targetPositionRepository.save(targets);
        }

        article.setTeam(team);
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setIsOpen(true);
        article.setCreatedAt(LocalDateTime.now());

        article = articleRepository.save(article);

        return ArticleResponseDTO.ArticleIdDTO.builder()
                .articleId(article.getId()).build();
    }
}