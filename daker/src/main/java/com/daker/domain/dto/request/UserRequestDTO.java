package com.daker.domain.dto.request;

public class UserRequestDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class LoginDTO {
        String loginId;
        String password;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class SignupDTO {
        String email;
        String loginId;
        String password;
        String name;
        String nickName;
    }
}