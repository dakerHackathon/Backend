package com.daker.domain.dto.response;

import lombok.*;

public class UserResponseDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO {
        Long userId;
    }
}
