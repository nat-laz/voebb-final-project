package com.example.voebb.repository;

import com.example.voebb.model.entity.CreatorRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorRoleRepo extends JpaRepository<CreatorRole, Long> {
}
