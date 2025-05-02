package com.example.voebb.repository;

import com.example.voebb.model.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepo extends JpaRepository<Work, Long> {
}
