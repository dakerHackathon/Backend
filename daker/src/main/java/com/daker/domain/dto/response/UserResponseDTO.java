package com.daker.domain.dto.response;

public class UserResponseDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class LoginDTO {
        Long userId;
    }
}
