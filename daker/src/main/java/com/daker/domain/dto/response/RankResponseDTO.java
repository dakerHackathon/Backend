package com.daker.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class RankResponseDTO {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopDTO {
        List<RankDTO> ranks;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopAllDTO {
        List<SimpleRankDTO> temp;
        List<SimpleRankDTO> win;
        List<SimpleRankDTO> part;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RankDTO {
        long id;
        String nickname;
        String github;
        float point;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimpleRankDTO {
        String nickname;
        float point;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyRankDTO {
        RankPointDTO temp;
        RankPointDTO win;
        RankPointDTO part;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RankPointDTO {
        int rank;
        float point;
    }
}
