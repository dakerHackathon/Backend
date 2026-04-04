package com.daker.domain.dto.response;

import com.daker.domain.dto.request.HackathonRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class HackathonResponseDTO {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HackathonResponseDTOBuilder {
        Long id;
        String title;
        String start_at;
        String end_at;
        String location;
        boolean isStar;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getHackathonList {
        List<HackathonResponseDTOBuilder> hackathons;
    }
}
