package com.gatekeeper.analytics.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import com.gatekeeper.analytics.dto.responsedto.HealthResponse;
import org.springframework.http.ResponseEntity;

@RestController 
@RequestMapping ("/health")
public class HealthController {
    
    private String servicename ;
    private String serviceDescription; ;

    public HealthController(@Value("${info.app.name}") String servicename, @Value("${info.app.description}") String serviceDescription) {
        this.servicename = servicename;
        this.serviceDescription = serviceDescription;
    }

    @GetMapping 
    public ResponseEntity<HealthResponse> health() {
        return ResponseEntity.ok
        (new HealthResponse(servicename, serviceDescription, "Up"));
    }
}
