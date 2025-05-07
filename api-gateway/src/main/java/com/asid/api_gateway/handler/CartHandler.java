package com.asid.api_gateway.handler;

import com.asid.api_gateway.dto.CartItemDTO;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CartHandler {

    private final WebClient.Builder webClientBuilder;

    public CartHandler(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Data
    static class Book {
        private Long id;
        private String title;
        private double price;
    }

    public Mono<ServerResponse> addBookToCart(ServerRequest request) {
        return request.bodyToMono(CartItemDTO.class)
            .flatMap(dto -> {
                Long bookId = dto.getBookId();
                int quantity = dto.getQuantity();

                return webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8080/books/" + bookId)
                    .retrieve()
                    .bodyToMono(Book.class)
                    .flatMap(book -> {
                        double unitPrice = book.getPrice();
                        double subTotal = unitPrice * quantity;

                        CartItemDTO enrichedDto = new CartItemDTO();
                        enrichedDto.setQuantity(quantity);
                        enrichedDto.setUnitPrice(unitPrice);
                        enrichedDto.setSubTotal(subTotal);
                        enrichedDto.setBookId(bookId);
                        enrichedDto.setUserId(dto.getUserId());

                        return webClientBuilder.build()
                            .post()
                            .uri("http://localhost:8080/cartitem")
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .bodyValue(enrichedDto)
                            .retrieve()
                            .bodyToMono(String.class)
                            .flatMap(response -> ServerResponse.ok().bodyValue(response));
                    });
            });
    }
}
