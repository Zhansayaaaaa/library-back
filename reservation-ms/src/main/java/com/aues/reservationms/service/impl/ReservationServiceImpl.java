package com.aues.reservationms.service.impl;
import com.aues.reservationms.exceptions.ReservationNotFoundException;
import com.aues.reservationms.model.Reservation;
import com.aues.reservationms.model.enums.ReservationStatus;
import com.aues.reservationms.repository.ReservationRepository;
import com.aues.reservationms.service.BookClientService;
import com.aues.reservationms.service.ReservationService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookClientService bookClientService;

    // Constructor-based dependency injection
    public ReservationServiceImpl(ReservationRepository reservationRepository, BookClientService bookClientService) {
        this.reservationRepository = reservationRepository;
        this.bookClientService = bookClientService;
    }

    @Override
    @Transactional
     public Reservation createReservation(Reservation reservation) {
        if (!bookClientService.isBookAvailable(reservation.getBookId())) {
            throw new RuntimeException("Book is not available for reservation");
        }
        reservation.setStatus(ReservationStatus.RESERVED);
        Reservation savedReservation = reservationRepository.save(reservation);
        bookClientService.decrementAvailableCopies(reservation.getBookId());
        return savedReservation;
    }

    @Override
    @Transactional
    public Reservation updateReservation(Long reservationId, Reservation updatedReservation) {
        return reservationRepository.findById(reservationId).map(reservation -> {
            // Check for null and update status if not null
            if (updatedReservation.getStatus() != null) {
                reservation.setStatus(updatedReservation.getStatus());
            }

            // Check for null and update reservation date if not null
            if (updatedReservation.getReservationDate() != null) {
                reservation.setReservationDate(updatedReservation.getReservationDate());
            }


            // Add similar null checks and updates for other fields as necessary
            // For example:
            // if (updatedReservation.getBookId() != null) {
            //     reservation.setBookId(updatedReservation.getBookId());
            // }

            // Save and return the updated reservation
            return reservationRepository.save(reservation);
        }).orElseThrow(() -> new ReservationNotFoundException("Reservation not found with ID: " + reservationId));
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with ID: " + reservationId));
    }

    @Override
    @Transactional
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with ID: " + reservationId));
        reservationRepository.delete(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations() {
        return
                reservationRepository.findAll();

    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByBookId(Long bookId) {
        return reservationRepository.findByBookId(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByStatus(ReservationStatus status) {
        return reservationRepository.findByStatus(status);
    }

    @Override
    public List<Reservation> searchReservations(Long userId, Long bookId, ReservationStatus status) {
        return reservationRepository.findAll((Specification<Reservation>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("userId"), userId));
            }

            if (bookId != null) {
                predicates.add(criteriaBuilder.equal(root.get("bookId"), bookId));
            }

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    // reservation-service/src/main/java/com/yourcompany/reservationservice/service/ReservationService.java
    public List<Reservation> getReservedReservations() {
        return reservationRepository.findByStatus(ReservationStatus.RESERVED);
    }

    // reservation-service/src/main/java/com/yourcompany/reservationservice/service/ReservationService.java
    public Reservation approveReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setStatus(ReservationStatus.LOANED);
        return reservationRepository.save(reservation);
    }


    @Transactional
    public Reservation updateReservationStatus(Long reservationId, String newStatus) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with ID: " + reservationId));

        // Convert the String to ReservationStatus enum
        ReservationStatus statusEnum = ReservationStatus.valueOf(newStatus);
        reservation.setStatus(statusEnum);

        Reservation updatedReservation = reservationRepository.save(reservation);

        if (ReservationStatus.RETURNED.equals(statusEnum)) {
            bookClientService.incrementAvailableCopies(reservation.getBookId());
        }

        return updatedReservation;
    }








    // Implement the optional searchReservations method if needed
}

