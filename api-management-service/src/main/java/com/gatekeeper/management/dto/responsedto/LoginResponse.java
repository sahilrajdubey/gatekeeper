package com.gatekeeper.management.dto.responsedto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class LoginResponse {
    private String token;
    private String tokenType;
    private long expiresIn;
    private String role;
    private long organizationId;
}
