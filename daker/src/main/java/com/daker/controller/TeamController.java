package com.daker.controller;

import com.daker.domain.dto.request.TeamRequestDTO;
import com.daker.domain.dto.response.TeamResponseDTO;
import com.daker.service.TeamService;
import com.daker.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/camp")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @RequestMapping("/{userId}/team")
    public ApiResponse<TeamResponseDTO.TeamIdDTO> createTeam(@PathVariable Long userId, @RequestBody TeamRequestDTO.CreateTeamDTO request) {
        try {
            TeamResponseDTO.TeamIdDTO data = teamService.createTeam(userId, request);
            return ApiResponse.onSuccess(data);
        } catch (Exception e) {
            System.out.println("failure return: " + e.getMessage());
            return ApiResponse.onFailure(e.getMessage());
        }
    }

    @PostMapping("/{userId}/team/{teamId}/register")
    public ApiResponse registerHackathon(@PathVariable Long userId, @PathVariable Long teamId, @RequestBody TeamRequestDTO.RegisterHackathonDTO request) {
        try {
            teamService.registerHackathon(userId, teamId, request.getHackathonId());
            return ApiResponse.onSuccess();
        } catch (Exception e) {
            System.out.println("failure return: " + e.getMessage());
            return ApiResponse.onFailure(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/team")
    public ApiResponse deleteTeam(@PathVariable Long userId, @RequestBody TeamRequestDTO.TeamIdDTO request) {
        try {
            teamService.deleteTeam(userId, request.getTeamId());
            return ApiResponse.onSuccess();
        } catch (Exception e) {
            System.out.println("failure return: " + e.getMessage());
            return ApiResponse.onFailure(e.getMessage());
        }
    }

    @PatchMapping("/{userId}/team/leave")
    public ApiResponse leaveTeam(@PathVariable Long userId, @RequestBody TeamRequestDTO.TeamIdDTO request) {
        try {
            teamService.leaveTeam(userId, request.getTeamId());
            return ApiResponse.onSuccess();
        } catch (Exception e) {
            System.out.println("failure return: " + e.getMessage());
            return ApiResponse.onFailure(e.getMessage());
        }
    }

    @PatchMapping("/{userId}/team/expell")
    public ApiResponse expellUser(@PathVariable Long userId, @RequestBody TeamRequestDTO.expellUserDTO request) {
        try {
            teamService.expellUser(request);
            return ApiResponse.onSuccess();
        } catch (Exception e) {
            System.out.println("failure return: " + e.getMessage());
            return ApiResponse.onFailure(e.getMessage());
        }
    }
}
