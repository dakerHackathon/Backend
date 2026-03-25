package com.daker.controller;

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

    @PostMapping("/login")
    public ApiResponse<UserResponseDTO.LoginDTO> login(@RequestBody UserRequestDTO.LoginDTO request) {
        try {
            UserResponseDTO.LoginDTO data = userService.login(request);
            return ApiResponse.onSuccess(data);
        }
        catch (Exception e) {
            System.out.println("failure return: " + e.getMessage());
            return ApiResponse.onFailure("404", e.getMessage());
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
            return ApiResponse.onFailure("404", e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ApiResponse withdrawalMembership(@PathVariable Long userId) {
        try {
            userService.withdrawalMembership(userId);
        } catch (Exception e) {
            System.out.println("failure return: " + e.getMessage());
            return ApiResponse.onFailure("404", e.getMessage());

        }
    }
}
