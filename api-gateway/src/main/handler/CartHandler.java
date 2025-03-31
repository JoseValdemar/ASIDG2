package com.asid.apigateway.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CartHandler {

    private final WebClient.Builder webClientBuilder;

    public CartHandler(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<ServerResponse> addBookToCart(ServerRequest request) {
        return request.bodyToMono(String.class) // recebe JSON genérico
            .flatMap(body -> webClientBuilder.build()
                .post()
                .uri("http://localhost:8081/cartitem") // mudar se necessário
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class))
            .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }
}
