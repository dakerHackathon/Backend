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
}
