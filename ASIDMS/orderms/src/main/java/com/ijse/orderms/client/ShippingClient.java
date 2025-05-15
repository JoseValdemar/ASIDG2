package com.ijse.orderms.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ShippingClient {

    private final WebClient webClient;

    @Autowired
    public ShippingClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://shippingms:8085").build();
    }

    public boolean shippingExists(Long shippingId) {
        try {
            return webClient.get()
                    .uri("/shipping/" + shippingId)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block() == null;
        } catch (Exception e) {
            return false;
        }
    }
}
