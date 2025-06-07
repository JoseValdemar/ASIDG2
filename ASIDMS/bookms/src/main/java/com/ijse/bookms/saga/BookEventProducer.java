package com.ijse.bookms.saga;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public BookEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(String event) {
        System.out.println("📤 [BOOKMS] Enviar evento para orquestrador: " + event);
        kafkaTemplate.send("saga-orchestrator", event);
    }
}
