package com.example.voebb.repository;

import com.example.voebb.model.entity.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDetailsRepo extends JpaRepository<BookDetails, Long> {
}
