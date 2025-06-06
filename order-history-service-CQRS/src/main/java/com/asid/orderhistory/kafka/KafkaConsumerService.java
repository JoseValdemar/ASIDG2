package com.asid.orderhistory.kafka;

import com.asid.orderhistory.client.UserClient;
import com.asid.orderhistory.client.BookClient;
import com.asid.orderhistory.client.ShippingClient;
import com.asid.orderhistory.entity.OrderHistory;
import com.asid.orderhistory.event.OrderCreatedEvent;
import com.asid.orderhistory.repository.OrderHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private BookClient bookClient;

    @Autowired
    private ShippingClient shippingClient;

    @KafkaListener(topics = "order-created", groupId = "order-history-group")
    public void consume(OrderCreatedEvent event) {
        try {
            if (orderHistoryRepository.existsById(event.getOrderId())) {
                logger.info("Encomenda já existe na base de dados de leitura: {}", event.getOrderId());
                return;
            }

            // Obter dados adicionais com fallback em caso de falha
            String userEmail = "indisponível";
            try {
                userEmail = userClient.getUserEmail(event.getUserId());
            } catch (Exception e) {
                logger.warn("Falha ao obter email do utilizador com ID {}: {}", event.getUserId(), e.getMessage());
            }

            String bookTitle = Mono.just("indisponível")
                .mergeWith(bookClient.getBookTitle(event.getBookId())
                    .timeout(java.time.Duration.ofSeconds(2))
                    .onErrorResume(ex -> {
                        logger.warn("Falha ao obter título do livro com ID {}: {}", event.getBookId(), ex.getMessage());
                        return Mono.empty();
                    })
                ).blockFirst();

            String shippingAddress = Mono.just("indisponível")
                .mergeWith(shippingClient.getShippingAddress(event.getShippingId())
                    .timeout(java.time.Duration.ofSeconds(2))
                    .onErrorResume(ex -> {
                        logger.warn("Falha ao obter morada de envio com ID {}: {}", event.getShippingId(), ex.getMessage());
                        return Mono.empty();
                    })
                ).blockFirst();

            LocalDateTime shippingDate = Mono.just(LocalDateTime.now())
                .mergeWith(shippingClient.getShippingDate(event.getShippingId())
                    .timeout(java.time.Duration.ofSeconds(2))
                    .onErrorResume(ex -> {
                        logger.warn("Falha ao obter data de envio com ID {}: {}", event.getShippingId(), ex.getMessage());
                        return Mono.empty();
                    })
                ).blockFirst();

            // Criar e persistir
            OrderHistory order = new OrderHistory();
            order.setOrderId(event.getOrderId());
            order.setUserId(event.getUserId());
            order.setUserEmail(userEmail);
            order.setOrderDate(event.getOrderDate());
            order.setTotalPrice(event.getTotalPrice());
            order.setShippingAddress(shippingAddress);
            order.setShippingDate(shippingDate);
            order.setBookTitles(bookTitle);

            orderHistoryRepository.save(order);
            logger.info("Encomenda registada na base de dados de leitura: {}", event.getOrderId());

        } catch (Exception ex) {
            logger.error("Erro ao processar evento OrderCreatedEvent: {}", ex.getMessage(), ex);
        }
    }
}
