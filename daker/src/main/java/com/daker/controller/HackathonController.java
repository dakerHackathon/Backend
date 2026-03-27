package com.daker.controller;

import com.daker.domain.dto.request.HackathonRequestDTO;
import com.daker.service.HackathonService;
import com.daker.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hackathons")
@RequiredArgsConstructor
public class HackathonController {

    private final HackathonService hackathonService;

    @PostMapping("/{userId}/save")
    public ApiResponse saveHackathon(@PathVariable Long userId, @RequestBody HackathonRequestDTO.HackathonIdDTO request) {
        try {
            hackathonService.saveHackathon(userId, request.getHackathonId());
            return ApiResponse.onSuccess();
        } catch (Exception e) {
            System.out.println("failure return: " + e.getMessage());
            return ApiResponse.onFailure(e.getMessage());
        }
    }
}
