package com.ijse.orderms.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BookClient {

    private final WebClient webClient;

    @Autowired
    public BookClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://bookms:8084").build();
    }

    public boolean bookExists(Long bookId) {
        try {
            return webClient.get()
                    .uri("/books/" + bookId)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block() == null;
        } catch (Exception e) {
            return false;
        }
    }
}
