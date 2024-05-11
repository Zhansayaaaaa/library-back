package com.aues.reservationms.service;

import com.aues.reservationms.exceptions.ReservationNotFoundException;
import com.aues.reservationms.model.Reservation;
import com.aues.reservationms.model.enums.ReservationStatus;

import java.util.List;

public interface ReservationService {

    // Create a new reservation
    Reservation createReservation(Reservation reservation);

    // Update an existing reservation
    Reservation updateReservation(Long reservationId, Reservation updatedReservation) throws ReservationNotFoundException;

    // Get a reservation by its ID
    Reservation getReservationById(Long reservationId) throws ReservationNotFoundException;

    // Delete a reservation by its ID
    void deleteReservation(Long reservationId) throws ReservationNotFoundException;

    // List all reservations
    List<Reservation> getAllReservations();

    // Find reservations by user ID
    List<Reservation> getReservationsByUserId(Long userId);

    // Find reservations by book ID
    List<Reservation> getReservationsByBookId(Long bookId);

    // Find reservations by status
    List<Reservation> getReservationsByStatus(ReservationStatus status);

    // Optional: Find reservations by a combination of criteria
    List<Reservation> searchReservations(Long userId, Long bookId, ReservationStatus status);

    List<Reservation> getReservedReservations();

    Reservation approveReservation(Long reservationId);
}
