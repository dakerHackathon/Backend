package com.daker.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class TeamResponseDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamIdDTO {
        Long teamId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamInfoListDTO {
        List<TeamInfoDTO> teams;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamInfoDTO {
        Long teamId;
        String teamName;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTeamDetailDTO {
        TeamDetailDTO team;
        List<TeamMemberDTO> member;
        HackathonDTO hackathon;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamDetailDTO {
        long teamId;
        String teamName;
        String description;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamMemberDTO {
        long userId;
        String nickName;
        int position;
        boolean isLeader;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HackathonDTO {
        long hackathonId;
        String hackathonName;
        String startAt;
        String endAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PositionsDTO {
        List<PositionDTO> positions;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PositionDTO {
        int id;
        String name;
        String abb;
    }
}
