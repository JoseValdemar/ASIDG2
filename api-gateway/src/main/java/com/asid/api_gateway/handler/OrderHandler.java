package com.asid.api_gateway.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import reactor.core.publisher.Mono;

@Component
public class OrderHandler {

    private final WebClient.Builder webClientBuilder;

    public OrderHandler(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<ServerResponse> getOrderDetails(ServerRequest request) {
        String orderId = request.pathVariable("orderId");

        Mono<JsonNode> orderMono = webClientBuilder.build()
            .get()
            .uri("http://localhost:8081/orders/" + orderId)
            .retrieve()
            .bodyToMono(JsonNode.class);

        Mono<JsonNode> userMono = webClientBuilder.build()
            .get()
            .uri("http://localhost:8081/users/1") // simulação
            .retrieve()
            .bodyToMono(JsonNode.class);

        Mono<JsonNode> shippingMono = webClientBuilder.build()
            .get()
            .uri("http://localhost:8081/shipping/" + orderId)
            .retrieve()
            .bodyToMono(JsonNode.class);

        return Mono.zip(orderMono, userMono, shippingMono)
            .map(tuple -> {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode composed = mapper.createObjectNode();
                composed.set("order", tuple.getT1());
                composed.set("user", tuple.getT2());
                composed.set("shipping", tuple.getT3());
                return composed;
            })
            .flatMap(composed -> ServerResponse.ok().bodyValue(composed));
    }
}
