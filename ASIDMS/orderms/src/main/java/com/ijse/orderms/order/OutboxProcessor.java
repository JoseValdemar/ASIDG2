package com.ijse.orderms.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ijse.orderms.event.OrderCreatedEvent;
import com.ijse.orderms.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutboxProcessor {

    @Autowired
    private OutboxEventRepository outboxEventRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Scheduled(fixedRate = 10000) // Executa a cada 10 segundos
    public void processPendingEvents() {
        List<OutboxEvent> events = outboxEventRepository.findByStatus("PENDING");
        for (OutboxEvent event : events) {
            try {
                OrderCreatedEvent orderCreated = objectMapper.readValue(event.getPayload(), OrderCreatedEvent.class);
                kafkaProducerService.sendOrderCreatedEvent(orderCreated);
                event.setStatus("SENT");
                outboxEventRepository.save(event);
            } catch (Exception e) {
                e.printStackTrace(); // Considera trocar por um logger
            }
        }
    }
}
