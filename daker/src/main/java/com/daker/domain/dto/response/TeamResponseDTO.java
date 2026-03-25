package com.daker.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class TeamResponseDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TeamListDTO {
        List<TeamIdandNameDTO> teams;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TeamIdandNameDTO {
        Long teamId;
        String teamName;
    }
}
