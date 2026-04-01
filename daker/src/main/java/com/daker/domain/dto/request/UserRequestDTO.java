package com.daker.domain.dto.request;

import lombok.*;

import java.util.List;

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

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EditInfoDTO {
        String nickName;
        String description;
        String portfolio;
        String github;
        List<SkillIdDTO> skills;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkillIdDTO {
        long id;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvitationIdDTO {
        long invitationId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SetTemperatureDTO {
        long userId;
        boolean plus;
    }
}