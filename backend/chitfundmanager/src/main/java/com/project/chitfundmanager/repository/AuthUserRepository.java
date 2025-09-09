package com.project.chitfundmanager.repository;

import com.project.chitfundmanager.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByMobileNumber(String mobileNumber);
    boolean existsByMobileNumber(String mobileNumber);
}
