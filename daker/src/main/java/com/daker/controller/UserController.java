package com.daker.controller;

import com.daker.domain.dto.request.MessageRequestDTO;
import com.daker.domain.dto.response.MessageResponseDTO;
import com.daker.service.MessageService;
import com.daker.service.UserService;
import com.daker.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import com.daker.domain.dto.request.UserRequestDTO;
import com.daker.domain.dto.response.UserResponseDTO;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MessageService messageService;

    @PostMapping("/login")
    public ApiResponse<UserResponseDTO.LoginDTO> login(@RequestBody UserRequestDTO.LoginDTO request) {
        UserResponseDTO.LoginDTO data = userService.login(request);
        return ApiResponse.onSuccess(data);
    }

    @PostMapping("/signup")
    public ApiResponse signup(@RequestBody UserRequestDTO.SignupDTO request) {
        userService.signup(request);
        return ApiResponse.onSuccess();
    }

    @DeleteMapping("/{userId}/delete")
    public ApiResponse withdrawalMembership(@PathVariable Long userId) {
        userService.withdrawalMembership(userId);
        return ApiResponse.onSuccess();
    }



    // 쪽지
    @GetMapping("/{userId}/message")
    public ApiResponse<MessageResponseDTO.MessagesDTO> getMessages(@PathVariable Long userId, @RequestParam(defaultValue = "all") String filter) {
        MessageResponseDTO.MessagesDTO data = messageService.getMessages(userId, filter);
        return ApiResponse.onSuccess(data);
    }

    @PostMapping("/{userId}/message/read")
    public ApiResponse readMessage(@PathVariable long userId, @RequestBody MessageRequestDTO.MessageIdDTO request) {
        messageService.readMessage(request.getMessageId());
        return ApiResponse.onSuccess();
    }

    @PostMapping("/{userId}/message/send")
    public ApiResponse sendMessage(@PathVariable Long userId, @RequestBody MessageRequestDTO.SendMessageDTO request) {
        messageService.sendMessage(userId, request);
        return ApiResponse.onSuccess();
    }

    @PostMapping("/{userId}/message/star")
    public ApiResponse starMessage(@PathVariable Long userId, @RequestBody MessageRequestDTO.MessageIdDTO request) {
        messageService.starMessage(request);
        return ApiResponse.onSuccess();
    }

    @DeleteMapping("/{userId}/message/delete")
    public ApiResponse deleteMessage(@PathVariable Long userId, @RequestBody MessageRequestDTO.MessageIdDTO request) {
        messageService.deleteMessage(request.getMessageId());
        return ApiResponse.onSuccess();
    }


    // 팀즈
    @GetMapping("/{userId}/invitations")
    public ApiResponse<UserResponseDTO.InvitationListDTO> GetInvitations(@PathVariable Long userId, @RequestParam String type) {
        UserResponseDTO.InvitationListDTO data = userService.getTeams(userId, type);
        return ApiResponse.onSuccess(data);
    }

    @DeleteMapping("/{userId}/invitations/delete")
    public ApiResponse deleteInvitation(@PathVariable Long userId, @RequestBody UserRequestDTO.InvitationIdDTO request) {
        userService.deleteInvitation(userId, request.getInvitationId());
        return ApiResponse.onSuccess();
    }



    // 마이페이지
    @GetMapping("/{userId}/mypage")
    public ApiResponse<UserResponseDTO.MyPageDTO> getMyPage(@PathVariable Long userId) {
        UserResponseDTO.MyPageDTO data = userService.getMyPage(userId);
        return ApiResponse.onSuccess(data);
    }

    @GetMapping("/{userId}/{teamId}/member")
    public ApiResponse<UserResponseDTO.UserInfoListDTO> getTeamMembers(@PathVariable Long userId, @PathVariable Long teamId) {
        UserResponseDTO.UserInfoListDTO data = userService.getTeamMembers(userId, teamId);
        return ApiResponse.onSuccess(data);
    }

    @PatchMapping("/{userId}/mypage")
    public ApiResponse editInfo(@PathVariable Long userId, @RequestBody UserRequestDTO.EditInfoDTO request) {
        userService.editInfo(userId, request);
        return ApiResponse.onSuccess();
    }


    // 온도 측정
    @GetMapping("/{userId}/temperature/{teamId}")
    public ApiResponse<UserResponseDTO.TemperatureSetListDTO> getTemperatureSetting(@PathVariable Long userId, @PathVariable Long teamId) {
        UserResponseDTO.TemperatureSetListDTO data = userService.getTemperatureSetting(userId, teamId);
    }


    // 검색
    @GetMapping("/search")
    public ApiResponse<UserResponseDTO.UserInfoListDTO> userSearch(@RequestParam String query) {
        UserResponseDTO.UserInfoListDTO data = userService.search(query);
        return ApiResponse.onSuccess(data);
    }
}
