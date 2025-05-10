package com.asid.orderhistory.client;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class ShippingClient {

    private final WebClient webClient;

    public ShippingClient(@Value("${shipping.service.url}") String shippingServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(shippingServiceUrl)
                .build();
    }

    public Mono<String> getShippingAddress(Long shippingId) {
        return webClient.get()
                .uri("/shipping/{id}/address", shippingId)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<LocalDateTime> getShippingDate(Long shippingId) {
        return webClient.get()
                .uri("/shipping/{id}/date", shippingId)
                .retrieve()
                .bodyToMono(LocalDateTime.class);
    }
}
