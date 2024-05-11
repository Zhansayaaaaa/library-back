package com.aues.reservationms.service;

public interface BookClientService {

    boolean isBookAvailable(Long bookId);

    void decrementAvailableCopies(Long bookId);

    void incrementAvailableCopies(Long bookId);
}
