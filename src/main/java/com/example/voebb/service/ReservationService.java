package com.example.voebb.service;

import com.example.voebb.model.dto.reservation.CreateReservationDTO;
import com.example.voebb.model.dto.reservation.GetReservationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReservationService {

    Page<GetReservationDTO> getFilteredReservations(Long userId, Long itemId, Long libraryId, Pageable pageable);

    void createReservation(CreateReservationDTO dto);

    void updateReservation(Long id, CreateReservationDTO dto);

    void deleteReservation(Long id);
}
