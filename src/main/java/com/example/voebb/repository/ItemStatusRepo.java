package com.example.voebb.repository;

import com.example.voebb.model.entity.ItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemStatusRepo extends JpaRepository<ItemStatus, Long> {
}
