package com.daker.controller;

import com.daker.domain.dto.request.MessageRequestDTO;
import com.daker.domain.dto.response.MessageResponseDTO;
import com.daker.service.MessageService;
import com.daker.service.UserService;
import com.daker.util.ApiResponse;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/{userId}/message/send")
    public ApiResponse sendMessage(@PathVariable Long userId, @RequestBody MessageRequestDTO.sendMessageDTO request) {
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


    // 검색
    @GetMapping("/search")
    public ApiResponse<UserResponseDTO.UserInfoDTO> userSearch(@RequestParam String query) {
        UserResponseDTO.UserInfoDTO data = userService.search(query);
        return ApiResponse.onSuccess(data);
    }
}
