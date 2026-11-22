package com.gatekeeper.management.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.gatekeeper.management.dto.requestdto.LoginRequest;
import com.gatekeeper.management.dto.responsedto.LoginResponse;
import com.gatekeeper.management.services.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@RestController 
@RequestMapping ("/api/auth")
@RequiredArgsConstructor 
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;

    @PostMapping ("/login")
        public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
            LoginResponse response = authenticationService.login(loginRequest);
            return ResponseEntity.ok(response);
        }

}
