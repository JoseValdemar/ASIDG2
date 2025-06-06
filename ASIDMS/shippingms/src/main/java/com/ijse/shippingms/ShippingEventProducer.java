package com.ijse.shippingms;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShippingEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ShippingEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendShippingStatus(String status) {
        kafkaTemplate.send("saga-orchestrator", status);
        System.out.println(" [SHIPPINGMS] Evento enviado para orquestrador: " + status);
    }
}
