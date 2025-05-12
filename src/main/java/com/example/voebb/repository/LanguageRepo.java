package com.example.voebb.repository;

import com.example.voebb.model.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepo extends JpaRepository<Language, Long> {

    Optional<Language> findByNameIgnoreCase(String name);
}
