package com.asid.api_gateway.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CartHandler {

    private final WebClient webClient;

    public CartHandler(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ServerResponse> addBookToCart(ServerRequest request) {
        return request.bodyToMono(String.class)
            .flatMap(body -> webClient.post()
                .uri("/cartitem") // vai fazer POST para http://localhost:8080/cartitem
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class))
            .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }
}
