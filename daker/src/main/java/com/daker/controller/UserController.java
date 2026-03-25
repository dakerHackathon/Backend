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
        try {
            UserResponseDTO.LoginDTO data = userService.login(request);
            return ApiResponse.onSuccess(data);
        }
        catch (Exception e) {
            System.out.println("failure return: " + e.getMessage());
            return ApiResponse.onFailure(e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ApiResponse signup(@RequestBody UserRequestDTO.SignupDTO request) {
        try {
            userService.signup(request);
            return ApiResponse.onSuccess(null);
        }
        catch (Exception e) {
            System.out.println("failure return: " + e.getMessage());
            return ApiResponse.onFailure(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ApiResponse withdrawalMembership(@PathVariable Long userId) {
        try {
            userService.withdrawalMembership(userId);
            return ApiResponse.onSuccess(null);
        } catch (Exception e) {
            System.out.println("failure return: " + e.getMessage());
            return ApiResponse.onFailure(e.getMessage());
        }
    }

    // 쪽지
    @GetMapping("/{userId}/message")
    public ApiResponse<MessageResponseDTO.MessagesDTO> getMessages(@PathVariable Long userId) {
        try {
            MessageResponseDTO.MessagesDTO data = messageService.getMessages(userId);
            return ApiResponse.onSuccess(data);
        } catch (Exception e) {
            System.out.println("failure return: " + e.getMessage());
            return ApiResponse.onFailure(e.getMessage());
        }
    }

    @PostMapping("/{userId}/message/send")
    public ApiResponse sendMessage(@PathVariable Long userId, @RequestBody MessageRequestDTO.sendMessageDTO request) {
        try {
            messageService.sendMessage(userId, request);
            return ApiResponse.onSuccess(null);
        } catch (Exception e) {
            System.out.println("failure return: " + e.getMessage());
            return ApiResponse.onFailure(e.getMessage());
        }
    }

    @PostMapping("/{userId}/message/star")
    public ApiResponse starMessage(@PathVariable Long userId, @RequestBody MessageRequestDTO.MessageIdDTO request) {
        try {
            messageService.starMessage(request);
            return ApiResponse.onSuccess(null);
        } catch (Exception e) {
            System.out.println("failure return: " + e.getMessage());
            return ApiResponse.onFailure(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/message/delete")
    public ApiResponse deleteMessage(@PathVariable Long userId, @RequestBody MessageRequestDTO.MessageIdDTO request) {
        try {
            messageService.deleteMessage(request.getMessageId());
            return ApiResponse.onSuccess(null);
        } catch (Exception e) {
            System.out.println("failure return: " + e.getMessage());
            return ApiResponse.onFailure(e.getMessage());
        }
    }
}
