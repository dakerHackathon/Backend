package com.daker.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class MessageResponseDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MessagesDTO {
        List<MessagesSimpleInfo> messages;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MessagesSimpleInfo {
        long id;
        String sender;
        String content;
        String send_at;
        boolean isRead;
        boolean isStar;
    }
}
