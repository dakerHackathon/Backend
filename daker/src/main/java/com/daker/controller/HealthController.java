package com.daker.controller;

@RestController
@RequiredArgsConstructor
public class HealthController {
    
    @GetMapping("/")
    public ApiResponse<> healthCheck() {
        System.out.println("Hello, World");
    }
}
