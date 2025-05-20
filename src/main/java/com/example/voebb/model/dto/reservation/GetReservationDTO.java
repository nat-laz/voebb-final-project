package com.example.voebb.model.dto.reservation;

import java.time.LocalDate;

public record GetReservationDTO(
        Long id,
        String customUserFullName,
        String itemTitle,
        LocalDate startDate,
        LocalDate dueDate
) {
}