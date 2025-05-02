package com.example.voebb.repository;

import com.example.voebb.model.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRepo extends JpaRepository<CustomUser, Long> {
}
