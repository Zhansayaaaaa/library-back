package com.aues.reservationms.exceptions;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(String message) {
        super(message);
    }

    // You can also overload the constructor to include the cause of the exception
    public ReservationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
