package com.gatekeeper.management.services;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@Service 
@RequiredArgsConstructor 
public class HealthService {

    public final JdbcTemplate jdbcTemplate;

    public String getHealth() {
        String databaseStatus = "Down";

        try{
            jdbcTemplate.execute("SELECT 1");
            databaseStatus = "Up";
        } catch (Exception e) {
            databaseStatus = "Down";
        }

        return databaseStatus;
    }
}
