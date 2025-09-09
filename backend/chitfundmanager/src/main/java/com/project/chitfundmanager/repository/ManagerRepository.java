package com.project.chitfundmanager.repository;

import com.project.chitfundmanager.model.AuthUser;
import com.project.chitfundmanager.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager,Long> {

    Optional<Manager> findByAuthUser(AuthUser user);
}
