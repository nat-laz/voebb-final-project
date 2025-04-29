package com.example.voebb.repository;

import com.example.voebb.model.entity.ItemLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemLocationRepo extends JpaRepository<ItemLocation, Long> {
}
