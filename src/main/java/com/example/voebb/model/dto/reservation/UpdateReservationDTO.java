package com.example.voebb.model.dto.reservation;

import java.time.LocalDate;

public record UpdateReservationDTO(Long id,
                                   Long userId,
                                   Long itemId,
                                   LocalDate startDate,
                                   LocalDate dueDate) {

}
