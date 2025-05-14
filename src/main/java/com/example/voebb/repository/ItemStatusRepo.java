package com.example.voebb.repository;

import com.example.voebb.model.entity.ItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemStatusRepo extends JpaRepository<ItemStatus, Long> {

    // ItemManagement
    @Query("""
                SELECT s FROM ItemStatus s
                WHERE LOWER(s.name) IN ('available', 'damaged', 'lost')
                ORDER BY 
                    CASE 
                        WHEN LOWER(s.name) = 'available' THEN 1
                        WHEN LOWER(s.name) = 'damaged' THEN 2
                        WHEN LOWER(s.name) = 'lost' THEN 3
                    END
            """)
    List<ItemStatus> filterEditableStatusesForItemManagement();

    // Borrow/ReserveManagement
    @Query("""
                SELECT s FROM ItemStatus s
                WHERE LOWER(s.name) IN ('available', 'reserved', 'borrowed')
                ORDER BY 
                    CASE 
                        WHEN LOWER(s.name) = 'available' THEN 1
                        WHEN LOWER(s.name) = 'reserved' THEN 2
                        WHEN LOWER(s.name) = 'borrowed' THEN 3
                    END
            """)
    List<ItemStatus> filterEditableStatusesForBorrowManagement();

    Optional<ItemStatus> findByNameIgnoreCase(String name);
}

