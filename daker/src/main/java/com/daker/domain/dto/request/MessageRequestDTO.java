package com.daker.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MessageRequestDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MessageIdDTO {
        long messageId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SendMessageDTO {
        String email;
        String title;
        String content;
    }
}
