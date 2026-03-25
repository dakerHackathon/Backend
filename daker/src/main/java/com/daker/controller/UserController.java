package com.daker.controller;

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
}
