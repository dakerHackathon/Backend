package com.daker.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ArticleResponseDTO {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArticleIdDTO {
        long articleId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetRecruitDTO {
        List<RecruitDTO> articles;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecruitDTO {
        ArticleInfoDTO article;
        CurrentTeamDTO team;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArticleInfoDTO {
        long id;
        String title;
        String content;
        List<PositionDTO> positions;
        boolean isOpen;
        String createdAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PositionDTO {
        int position;
        int headCount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurrentTeamDTO {
        long id;
        String name;
        List<Integer> positions;
        ConnectedHackathonDTO hackathon;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConnectedHackathonDTO {
        long hackathonId;
        String hackathonTitle;
    }
}
