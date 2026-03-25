package com.daker.controller;

import com.daker.domain.dto.request.TeamRequestDTO;
import com.daker.domain.dto.response.TeamResponseDTO;
import com.daker.service.TeamService;
import com.daker.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/camp")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @GetMapping("")
    public ApiResponse<TeamResponseDTO.TeamListDTO> getTeams() {
        try {
            TeamResponseDTO.TeamListDTO data = teamService.getTeams();
            return ApiResponse.onSuccess(data);
        } catch (Exception e) {
            System.out.println("failure return: " + e.getMessage());
            return ApiResponse.onFailure(e.getMessage());
        }
    }
}
