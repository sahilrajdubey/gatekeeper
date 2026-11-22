package com.gatekeeper.management.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gatekeeper.management.entity.ManagementUser;

public interface ManagementUserRepo extends JpaRepository<ManagementUser, Long> {
    Optional<ManagementUser> findByUsername(String username);
}
