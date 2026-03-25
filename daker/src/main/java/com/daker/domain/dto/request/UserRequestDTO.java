package com.daker.domain.dto.request;

import lombok.*;

public class UserRequestDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO {
        String loginId;
        String password;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignupDTO {
        String email;
        String loginId;
        String password;
        String name;
        String nickName;
    }
}