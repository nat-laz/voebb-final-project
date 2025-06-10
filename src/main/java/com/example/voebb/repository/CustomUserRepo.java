package com.example.voebb.repository;

import com.example.voebb.model.dto.user.GetCustomUserDTO;
import com.example.voebb.model.entity.CustomUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomUserRepo extends JpaRepository<CustomUser, Long> {
    Optional<CustomUser> findByEmail(String username);

    Optional<CustomUser> findByPhoneNumber(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query("""
               SELECT new com.example.voebb.model.dto.user.GetCustomUserDTO(
                   u.id,
                   CONCAT(u.firstName, ' ', u.lastName),
                   u.email,
                   u.phoneNumber,
                   u.isEnabled,
                   u.borrowedProductsCount
               )
               FROM CustomUser u
               WHERE (:userId IS NULL OR u.id = :userId)
                 AND (:email IS NULL OR :email = '' OR u.email ILIKE '%' || :email || '%')
                 AND (:name IS NULL or :name = '' OR u.firstName ILIKE '%' || :name || '%' OR u.lastName ILIKE '%' || :name || '%')
                 AND (:isEnabled IS NULL OR u.isEnabled = :isEnabled)
           """)
    Page<GetCustomUserDTO> findFilteredUsers(
            @Param("userId") Long userId,
            @Param("email") String email,
            @Param("name") String name,
            @Param("isEnabled") Boolean isEnabled,
            Pageable pageable
    );

}
