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
}
