package com.aues.loanms.service.impl;

import com.aues.loanms.service.BookServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
@RequiredArgsConstructor
public class BookServiceClientImpl implements BookServiceClient {

    private final RestTemplate restTemplate;
    @Value("${book.service.url}")
    private String bookServiceUrl;
// Injected or hard-coded


    public void incrementAvailableCopies(Long bookId) {
        restTemplate.postForObject(bookServiceUrl + "/books/" + bookId + "/increment", null, Void.class);
    }
}