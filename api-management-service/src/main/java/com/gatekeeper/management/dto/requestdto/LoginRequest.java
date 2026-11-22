package com.gatekeeper.management.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class LoginRequest {

    @NotBlank 
    String username;
    @NotBlank 
    String password;
}
