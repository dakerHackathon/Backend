package com.daker.controller;

import com.daker.domain.dto.response.RankResponseDTO;
import com.daker.service.RankService;
import com.daker.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rankings")
@RequiredArgsConstructor
public class RankController {
    private final RankService rankService;

    @GetMapping("/")
    public ApiResponse<RankResponseDTO.TopDTO> getRankings(@RequestParam String filter) {
        RankResponseDTO.TopDTO data = rankService.getRankings(filter);
        return ApiResponse.onSuccess(data);
    }

    @GetMapping("/{userId}")
    public ApiResponse<RankResponseDTO.MyRankDTO> getMyRanking(@PathVariable Long userId) {
        RankResponseDTO.MyRankDTO data = rankService.getMyRanking(userId);
        return ApiResponse.onSuccess(data);
    }

    @GetMapping("/rankings/top3")
    public ApiResponse<RankResponseDTO.TopAllDTO> getTop3() {
        RankResponseDTO.TopAllDTO data = rankService.getTop3();
        return ApiResponse.onSuccess(data);
    }
}
