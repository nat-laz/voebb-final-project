package com.example.voebb.repository;

import com.example.voebb.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepo extends JpaRepository<Country, Long> {

    Optional<Country> findByNameIgnoreCase(String name);

    Country findByName(String name);

    boolean existsByName(String name);
}
