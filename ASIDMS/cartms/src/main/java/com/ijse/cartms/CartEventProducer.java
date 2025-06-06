package com.ijse.cartms;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CartEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public CartEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCartStatus(String status) {
        kafkaTemplate.send("saga-orchestrator", status);
        System.out.println(" [CARTMS] Evento enviado para orquestrador: " + status);
    }
}

