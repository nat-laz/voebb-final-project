package com.example.voebb.job;

import com.example.voebb.model.entity.CustomUser;
import com.example.voebb.model.entity.ProductItem;
import com.example.voebb.model.entity.Reservation;
import com.example.voebb.repository.CustomUserRepo;
import com.example.voebb.repository.ProductItemRepo;
import com.example.voebb.repository.ReservationRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ReservationCleanupJobTest {

    @Autowired
    private ReservationCleanupJob cleanupJob;

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private CustomUserRepo userRepo;

    @Autowired
    private ProductItemRepo itemRepo;

    @Test
    void testRemoveExpiredReservations() {
        reservationRepo.deleteAll();

        // Arrange
        CustomUser user = new CustomUser();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@example.com");
        user.setPhoneNumber("1234567890");
        user.setPassword("password");
        user.setEnabled(true);
        user.setBorrowedProductsCount(0);
        userRepo.save(user);

        ProductItem item = new ProductItem();
        itemRepo.save(item);

        LocalDate today = LocalDate.now();
        Reservation expiredRes1 = new Reservation(null, user, item, today.minusDays(5), today.minusDays(2));
        Reservation expiredRes2 = new Reservation(null, user, item, today.minusDays(4), today.minusDays(1));
        Reservation validRes1 = new Reservation(null, user, item, today, today.plusDays(3));
        Reservation validRes3 = new Reservation(null, user, item, today.minusDays(1), today.plusDays(2));

        reservationRepo.saveAll(List.of(expiredRes1, expiredRes2, validRes1, validRes3));

        // Act
        cleanupJob.removeExpiredReservations();

        // Assert
        List<Reservation> remaining = reservationRepo.findAll();
        assertThat(remaining).allMatch(r -> !r.getDueDate().isBefore(today));
        assertThat(remaining).hasSize(2);
    }

}