package com.daker.service;


import com.daker.domain.dto.request.TeamRequestDTO;
import com.daker.domain.dto.response.TeamResponseDTO;
import com.daker.domain.entity.*;
import com.daker.repository.*;
import com.daker.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}