package com.daker.domain.dto.response;

import com.daker.domain.dto.request.HackathonRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HackathonResponseDTOSchedule {
        String scheduleName;
        String scheduleTime;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HackathonResponseDTOEval {
        String name;
        int percent;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HackathonResponseDTOTeam {
        Long teamId;
        String teamName;
        int number;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HackathonResponseDTOLeaderBoard {
        Long teamId;
        String teamName;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getHackathonDetail {
        Long hackathonId;
        String hackathonTitle;
        String hackathonSubTitle;
        String description;
        String organizer;
        String location;
        boolean isStar;
        List<HackathonResponseDTOSchedule> schedule;
        String submissionGuide;
        List<HackathonResponseDTOEval> evaluationCriteria;
        List<Integer> prize;
        List<HackathonResponseDTOTeam> teams;
        List<HackathonResponseDTOLeaderBoard> leaderBoard;
    }


}
