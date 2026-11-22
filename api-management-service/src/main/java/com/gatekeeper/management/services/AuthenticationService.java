package com.gatekeeper.management.services;

import org.springframework.stereotype.Service;
import com.gatekeeper.management.repository.ManagementUserRepo;

import org.springframework.transaction.annotation.Transactional;
import com.gatekeeper.management.dto.requestdto.LoginRequest;
import com.gatekeeper.management.dto.responsedto.LoginResponse;
import com.gatekeeper.management.entity.ManagementUser;
import com.gatekeeper.management.exception.ApiException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import java.time.Instant;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.http.HttpStatus;
import com.gatekeeper.management.entity.OrganizationStatus;

@Service
public class AuthenticationService {

    private final ManagementUserRepo managementUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final long expirationSeconds;

    public AuthenticationService(ManagementUserRepo managementUserRepository,
                                 PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder,
                                 @Value("${security.jwt.expiration-seconds}") long expirationSeconds) {
        this.managementUserRepository = managementUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.expirationSeconds = expirationSeconds;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        ManagementUser user = managementUserRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> invalidCredentials());

        if(!user.isEnabled() || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw invalidCredentials();
        }

        if(user.getOrganization() != null && user.getOrganization().getStatus() != OrganizationStatus.ACTIVE) {
            throw new ApiException(HttpStatus.FORBIDDEN, "The organization is not active");
        }

        Instant issuedAt = Instant.now();
        String role = user.getRole().getName();
        Long organizationId = user.getOrganization() == null ? null : user.getOrganization().getId();

        JwtClaimsSet.Builder claims = JwtClaimsSet.builder()
                .issuer("gatekeeper-api-management")
                .issuedAt(issuedAt)
                .expiresAt(issuedAt.plusSeconds(expirationSeconds))
                .subject(user.getUsername())
                .claim("role", role);

        if(organizationId != null) {
            claims.claim("organizationId", organizationId);
        }

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        String token = jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims.build())).getTokenValue();

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setTokenType("Bearer");
        response.setExpiresIn(expirationSeconds);
        response.setRole(role);
        response.setOrganizationId(organizationId != null ? organizationId : -1);   
        return response;
    }

    private RuntimeException invalidCredentials() {
        return new ApiException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }
}