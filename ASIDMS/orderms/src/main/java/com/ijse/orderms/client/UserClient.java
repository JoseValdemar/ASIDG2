package com.ijse.orderms.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserClient {

    private final WebClient webClient;

    @Autowired
    public UserClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://userms:8083").build();
    }

    public boolean userExists(Long userId) {
        try {
            return webClient.get()
                    .uri("/user/" + userId)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block() == null; // Apenas verificar se retorna 200 OK
        } catch (Exception e) {
            return false;
        }
    }
}
