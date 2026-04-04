package com.daker.controller;

import com.daker.domain.dto.request.ArticleRequestDTO;
import com.daker.domain.dto.request.TeamRequestDTO;
import com.daker.domain.dto.response.ArticleResponseDTO;
import com.daker.domain.dto.response.TeamResponseDTO;
import com.daker.service.TeamService;
import com.daker.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
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

    @GetMapping("/positions")
    public ApiResponse<TeamResponseDTO.PositionsDTO> getPositions() {
        TeamResponseDTO.PositionsDTO data = teamService.getPositions();
        return ApiResponse.onSuccess(data);
    }

    @GetMapping("/{userId}/recruit")
    public ApiResponse<ArticleResponseDTO.GetRecruitDTO> getRecruit(@PathVariable Long userId, @RequestParam(required = false) String open, @RequestParam(required = false) String position) {
        ArticleResponseDTO.GetRecruitDTO data = teamService.getArticles(open, position);
        return ApiResponse.onSuccess(data);
    }

    @GetMapping("/{userId}/recruit/search")
    public ApiResponse<ArticleResponseDTO.GetRecruitDTO> searchRecruit(@PathVariable Long userId, @RequestParam String filter, @RequestParam String query) {
        ArticleResponseDTO.GetRecruitDTO data = teamService.searchRecruit(userId, filter, query);
        return ApiResponse.onSuccess(data);
    }
    // 모집 공고글 수정
    @PatchMapping("/{userId}/recruit/{articleId}")
    public ApiResponse<ArticleResponseDTO.ArticleIdDTO> updateAriticle(@PathVariable Long userId, @PathVariable Long articleId, @RequestBody ArticleRequestDTO.CreateArticleDTO request){
        ArticleResponseDTO.ArticleIdDTO data = teamService.updateArticle(userId, articleId, request);
        return ApiResponse.onSuccess(data);
    }

    @DeleteMapping("/{userId}/recruit")
    public ApiResponse deleteAriticle(@PathVariable Long userId, @RequestBody ArticleRequestDTO.ArticleIdDTO request) {
        teamService.deleteArticle(userId, request);
        return ApiResponse.onSuccess();
    }

    @PostMapping("/{userId}/recruit/close")
    public ApiResponse<ArticleResponseDTO.ArticleIdDTO> deadlineArticle(@PathVariable Long userId, @RequestBody ArticleRequestDTO.ArticleIdDTO request){
        ArticleResponseDTO.ArticleIdDTO data = teamService.deadlineArticle(userId, request);
        return ApiResponse.onSuccess(data);
    }


    // 팀 초대/참가
    @PostMapping("/{userId}/invite/{teamId}")
    public ApiResponse invite(@PathVariable Long userId, @PathVariable Long teamId, @RequestBody TeamRequestDTO.InviteMemberDTO request) {
        teamService.invite(userId, teamId, request);
        return ApiResponse.onSuccess();
    }

    @PostMapping("/{userId}/join")
    public ApiResponse join(@PathVariable Long userId, @RequestBody TeamRequestDTO.JoinDTO request) {
        teamService.join(userId, request);
        return ApiResponse.onSuccess();
    }

    @PostMapping("/{userId}/answer")
    public ApiResponse answer(@PathVariable Long userId, @RequestBody TeamRequestDTO.AnswerDTO request) {
        teamService.answer(userId, request);
        return ApiResponse.onSuccess();
    }
}
