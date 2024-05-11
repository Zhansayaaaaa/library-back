package com.aues.reservationms.controller;
import com.aues.reservationms.dto.ReservationSearchCriteria;
import com.aues.reservationms.exceptions.ReservationNotFoundException;
import com.aues.reservationms.model.Reservation;
import com.aues.reservationms.model.enums.ReservationStatus;
import com.aues.reservationms.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reservations")
@CrossOrigin(origins = "http://localhost:5173")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        try {
            Reservation createdReservation = reservationService.createReservation(reservation);
            return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<?> updateReservation(@PathVariable Long reservationId, @RequestBody Reservation updatedReservation) {
        try {
            Reservation reservation = reservationService.updateReservation(reservationId, updatedReservation);
            return ResponseEntity.ok(reservation);
        } catch (ReservationNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<?> getReservationById(@PathVariable Long reservationId) {
        try {
            Reservation reservation = reservationService.getReservationById(reservationId);
            return ResponseEntity.ok(reservation);
        } catch (ReservationNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long reservationId) {
        try {
            reservationService.deleteReservation(reservationId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ReservationNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        try {
            List<Reservation> reservations = reservationService.getAllReservations();
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Inside the ReservationController class

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getReservationsByUserId(@PathVariable Long userId) {
        try {
            List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<?> getReservationsByBookId(@PathVariable Long bookId) {
        try {
            List<Reservation> reservations = reservationService.getReservationsByBookId(bookId);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getReservationsByStatus(@PathVariable ReservationStatus status) {
        try {
            List<Reservation> reservations = reservationService.getReservationsByStatus(status);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchReservations(@RequestBody ReservationSearchCriteria searchCriteria) {
        try {
            List<Reservation> reservations = reservationService.searchReservations(searchCriteria.getUserId(), searchCriteria.getBookId(), searchCriteria.getStatus());
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // reservation-service/src/main/java/com/yourcompany/reservationservice/controller/ReservationController.java
    @GetMapping("/reservations/reserved")
    public ResponseEntity<List<Reservation>> getReservedReservations() {
        List<Reservation> reservedReservations = reservationService.getReservedReservations();
        return ResponseEntity.ok(reservedReservations);
    }

    // reservation-service/src/main/java/com/yourcompany/reservationservice/controller/ReservationController.java
    @PostMapping("/reservations/{reservationId}/approve")
    public ResponseEntity<Reservation> approveReservation(@PathVariable Long reservationId) {
        Reservation approvedReservation = reservationService.approveReservation(reservationId);
        return ResponseEntity.ok(approvedReservation);
    }







    // Additional endpoints for getReservationsByUserId, getReservationsByBookId, getReservationsByStatus, and searchReservations can be added here, following a similar pattern.
}

