package com.daker.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ArticleRequestDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateArticleDTO {
        String title;
        String content;
        List<lookingForDTO> lookingFor;
        String contact;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class lookingForDTO {
        long positionId;
        int headCount;
    }
}
