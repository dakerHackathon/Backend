package com.daker.controller;

import com.daker.domain.dto.request.ArticleRequestDTO;
import com.daker.domain.dto.request.TeamRequestDTO;
import com.daker.domain.dto.response.ArticleResponseDTO;
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

    @GetMapping("/{userId}/team/{teamId}")
    public ApiResponse<TeamResponseDTO.GetTeamDetailDTO> getTeamDetail(@PathVariable Long userId, @PathVariable Long teamId) {
        TeamResponseDTO.GetTeamDetailDTO data = teamService.getTeamDetail(userId, teamId);
        return ApiResponse.onSuccess(data);
    }

    @GetMapping("/{userId}/team")
    public ApiResponse<TeamResponseDTO.TeamInfoListDTO> getOwnTeams(@PathVariable Long userId) {
        TeamResponseDTO.TeamInfoListDTO data = teamService.getOwnTeams(userId);
        return ApiResponse.onSuccess(data);
    }

    @PostMapping("/{userId}/team")
    public ApiResponse<TeamResponseDTO.TeamIdDTO> createTeam(@PathVariable Long userId, @RequestBody TeamRequestDTO.CreateTeamDTO request) {
        TeamResponseDTO.TeamIdDTO data = teamService.createTeam(userId, request);
        return ApiResponse.onSuccess(data);
    }

    @PostMapping("/{userId}/team/{teamId}/register")
    public ApiResponse registerHackathon(@PathVariable Long userId, @PathVariable Long teamId, @RequestBody TeamRequestDTO.RegisterHackathonDTO request) {
        teamService.registerHackathon(userId, teamId, request.getHackathonId());
        return ApiResponse.onSuccess();
    }

    @DeleteMapping("/{userId}/team")
    public ApiResponse deleteTeam(@PathVariable Long userId, @RequestBody TeamRequestDTO.TeamIdDTO request) {
        teamService.deleteTeam(userId, request.getTeamId());
        return ApiResponse.onSuccess();
    }

    @PatchMapping("/{userId}/team/leave")
    public ApiResponse leaveTeam(@PathVariable Long userId, @RequestBody TeamRequestDTO.TeamIdDTO request) {
        teamService.leaveTeam(userId, request.getTeamId());
        return ApiResponse.onSuccess();
    }

    @PatchMapping("/{userId}/team/expell")
    public ApiResponse expellUser(@PathVariable Long userId, @RequestBody TeamRequestDTO.ExpellUserDTO request) {
        teamService.expellUser(request);
        return ApiResponse.onSuccess();
    }

    @PatchMapping("/{userId}/team/{teamId}")
    public ApiResponse<TeamResponseDTO.TeamIdDTO> editTeamInfo(@PathVariable Long userId, @PathVariable Long teamId, @RequestBody TeamRequestDTO.EditTeamInfoDTO request) {
        TeamResponseDTO.TeamIdDTO data = teamService.editTeam(userId, teamId, request);
        return ApiResponse.onSuccess(data);
    }

    @PatchMapping("/{userId}/team/{teamId}/member")
    public ApiResponse editTeamMember(@PathVariable Long userId, @PathVariable Long teamId, @RequestBody TeamRequestDTO.MemberEditDTO request) {
        teamService.changeRole(userId, teamId, request);
        return ApiResponse.onSuccess();
    }


    // 모집 공고글 관련 API
    @PostMapping("/{userId}/recruit/{teamId}")
    public ApiResponse<ArticleResponseDTO.ArticleIdDTO> createArticle(@PathVariable Long userId, @PathVariable Long teamId, @RequestBody ArticleRequestDTO.CreateArticleDTO request) {
        ArticleResponseDTO.ArticleIdDTO data = teamService.createArticle(userId, teamId, request);
        return ApiResponse.onSuccess(data);
    }
}
