package com.example.voebb.repository;

import com.example.voebb.model.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepo extends JpaRepository<Language, Long> {
}
