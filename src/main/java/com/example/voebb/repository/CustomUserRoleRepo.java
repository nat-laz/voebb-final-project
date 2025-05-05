package com.example.voebb.repository;

import com.example.voebb.model.entity.CustomUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomUserRoleRepo extends JpaRepository<CustomUserRole, Long> {
    Optional<CustomUserRole> findByName(String name);
}
