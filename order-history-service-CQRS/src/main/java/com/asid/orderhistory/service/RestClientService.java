package com.asid.orderhistory.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RestClientService {

    private final WebClient userClient = WebClient.create("http://userms:8080");
    private final WebClient bookClient = WebClient.create("http://bookms:8080");
    private final WebClient shippingClient = WebClient.create("http://shippingms:8080");

    public Mono<String> getUserNameById(Long userId) {
        return userClient.get()
                .uri("/users/" + userId)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getBookTitleById(Long bookId) {
        return bookClient.get()
                .uri("/book/" + bookId) 
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getShippingAddressById(Long shippingId) {
        return shippingClient.get()
                .uri("/shipping/" + shippingId)
                .retrieve()
                .bodyToMono(String.class);
    }
}
