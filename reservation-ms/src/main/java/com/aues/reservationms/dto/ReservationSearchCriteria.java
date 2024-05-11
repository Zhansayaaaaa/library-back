package com.aues.reservationms.dto;

import com.aues.reservationms.model.enums.ReservationStatus;
import lombok.Data;

@Data
public class ReservationSearchCriteria {
    private Long userId;
    private Long bookId;
    private ReservationStatus status;

    // Constructors, getters, and setters
}

