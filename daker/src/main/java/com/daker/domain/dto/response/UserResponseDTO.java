package com.daker.domain.dto.response;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponseDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO {
        Long userId;
        String userNickname;
        String userEmail;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPageDTO {
        String email;
        String nickname;
        String description;
        String portfolio;
        String github;
        List<SkillIdDTO> stills;
        int point;
        float temperature;
        int rank;
        int winCount;
        int partCount;
        List<PartHackathonDTO> part_hackathon;
        List<PartTeamsDTO> teams;
        List<SaveHackathonDTO> save_hackathon;
        int unread;
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
    public static class PartHackathonDTO {
        long hackathonId;
        String hackathonName;
        String start;
        String end;
        int position;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PartTeamsDTO {
        long teamId;
        String teamName;
        String description;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveHackathonDTO {
        long hackathonId;
        String hackathonName;
        String end;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoListDTO {
        List<UserInfoDTO> users;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoDTO {
        long userId;
        String userName;
        String userEmail;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvitationListDTO {
        List<InvitationDTO> invitations;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InvitationDTO {
        long invitationId;
        String title;
        String content;
        int type;
        InvitationSenderDTO sender;
        int position;
        LocalDateTime created_at;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvitationSenderDTO {
        long userId;
        String userName;
        String teamName;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TemperatureSetListDTO {
        List<TemperatureSetDTO> members;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TemperatureSetDTO {
        long userId;
        String userNickName;
        String userEmail;
        boolean canSet;
    }
}