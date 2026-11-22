package com.gatekeeper.management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gatekeeper.management.dto.responsedto.HealthResponse;
import org.springframework.http.ResponseEntity;
import com.gatekeeper.management.services.*;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/health")
@RequiredArgsConstructor 

public class HealthController {

    private final HealthService healthService;

    @GetMapping 
    public ResponseEntity<HealthResponse> health() {
        String dbStatus = healthService.getHealth();
        return ResponseEntity.ok(
            new HealthResponse("Up", dbStatus, java.time.LocalDateTime.now())
        );
    }


}
