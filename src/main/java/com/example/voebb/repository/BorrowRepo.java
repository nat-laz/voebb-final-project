package com.example.voebb.repository;

import com.example.voebb.model.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepo extends JpaRepository<Borrow, Long> {
}
