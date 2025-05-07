package com.asid.api_gateway.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class OrderHandler {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<ServerResponse> getOrderDetails(ServerRequest request) {
        String orderId = request.pathVariable("id");

        // Chamada única ao backend que já devolve todos os dados
        Mono<JsonNode> composedMono = webClientBuilder.build()
            .get()
            .uri("http://localhost:8080/orderdetails/" + orderId)
            .retrieve()
            .bodyToMono(JsonNode.class);

        return composedMono
            .flatMap(json -> ServerResponse.ok().bodyValue(json));
    }
}