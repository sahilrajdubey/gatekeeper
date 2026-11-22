package com.gatekeeper.management.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor

public class HealthResponse {
    private String serviceStatus;
    private String dbStatus;
    private LocalDateTime timestamp;
}
