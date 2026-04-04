package com.daker.controller;

import com.daker.domain.dto.request.HackathonRequestDTO;
import com.daker.domain.dto.response.HackathonResponseDTO;
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
        hackathonService.saveHackathon(userId, request.getHackathonId());
        return ApiResponse.onSuccess();
    }

    @GetMapping()
    public ApiResponse<HackathonResponseDTO.getHackathonList> hackathons(@PathVariable Long userId){
        HackathonResponseDTO.getHackathonList data = hackathonService.hackathons(userId);
        return ApiResponse.onSuccess(data);
    }

}
