package com.aues.reservationms.service.impl;

import com.aues.reservationms.service.BookClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookClientServiceImpl implements BookClientService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${book.service.url}")
    private String bookServiceUrl;

    public boolean isBookAvailable(Long bookId) {
        String url = bookServiceUrl + "/books/" + bookId + "/availability";
        return restTemplate.getForObject(url, Boolean.class);
    }

    public void decrementAvailableCopies(Long bookId) {
        String url = bookServiceUrl + "/books/" + bookId + "/decrement";
        restTemplate.postForLocation(url, null);
    }
    @Override
    public void incrementAvailableCopies(Long bookId) {
        String url = bookServiceUrl + "/books/" + bookId + "/increment";
        restTemplate.postForLocation(url, null);
    }


}
