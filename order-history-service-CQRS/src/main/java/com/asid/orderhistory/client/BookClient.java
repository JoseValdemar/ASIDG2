package com.asid.orderhistory.client;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class BookClient {

    private final WebClient webClient;

    public BookClient(@Value("${book.service.url}") String bookServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(bookServiceUrl)
                .build();
    }

    public Mono<Double> getBookPrice(Long bookId) {
        return webClient.get()
                .uri("/books/{id}/price", bookId)
                .retrieve()
                .bodyToMono(Double.class);
    }

    public Mono<String> getBookTitle(Long bookId) {
        return webClient.get()
                .uri("/books/{id}/title", bookId)
                .retrieve()
                .bodyToMono(String.class);
    }
}
