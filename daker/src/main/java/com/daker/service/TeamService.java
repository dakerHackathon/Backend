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

}