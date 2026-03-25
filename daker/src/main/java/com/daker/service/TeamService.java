package com.daker.service;


import com.daker.domain.dto.response.TeamResponseDTO;
import com.daker.domain.entity.Team;
import com.daker.domain.entity.User;
import com.daker.repository.TeamRepository;
import com.daker.repository.UserTeamRepository;
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

    public TeamResponseDTO.TeamListDTO getTeams() {
        List<Team> teamList = teamRepository.findAll();

        List<Team> topTeams = teamList.stream()
                .sorted((t1, t2) ->
                        Integer.compare(getTotalPoint(t2), getTotalPoint(t1)) // 내림차순
                )
                .limit(10)
                .toList();

        List<TeamResponseDTO.TeamIdandNameDTO> result = topTeams.stream()
                .map(team -> TeamResponseDTO.TeamIdandNameDTO.builder()
                        .teamId(team.getId())
                        .teamName(team.getName()).build())
                .toList();

        return TeamResponseDTO.TeamListDTO.builder()
                .teams(result).build();
    }

    private int getTotalPoint(Team team) {
        return team.getUserTeams().stream()
                .mapToInt(ut -> ut.getUser().getPoint())
                .sum();
    }
}