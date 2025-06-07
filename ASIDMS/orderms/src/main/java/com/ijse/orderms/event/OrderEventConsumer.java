package com.ijse.orderms.event;

import com.ijse.orderms.dto.OrderDetailsDTO;
import com.ijse.orderms.order.OrderDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    private final OrderDetailsService orderDetailsService;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderEventConsumer(OrderDetailsService orderDetailsService, KafkaTemplate<String, String> kafkaTemplate) {
        this.orderDetailsService = orderDetailsService;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "saga-order", groupId = "orderms-group")
    public void listen(String message) {
        System.out.println("📥 [ORDERMS] Evento recebido: " + message);
        try {
            // Tentar fazer parse do JSON para criar uma encomenda
            OrderDetailsDTO dto = objectMapper.readValue(message, OrderDetailsDTO.class);
            orderDetailsService.createOrder(dto);
            kafkaTemplate.send("saga-orchestrator", "ORDER_CREATED");
        } catch (Exception e) {
            System.out.println(" Falha ao criar encomenda: " + e.getMessage());
            kafkaTemplate.send("saga-orchestrator", "ORDER_FAILED");
        }
    }
}
