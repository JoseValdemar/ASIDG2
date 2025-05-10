package com.ijse.orderms.order;

import com.ijse.orderms.event.OrderCreatedEvent;
import com.ijse.orderms.kafka.KafkaProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OutboxProcessor {

    @Autowired
    private OutboxEventRepository outboxEventRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Scheduled(fixedRate = 5000)
    public void processOutbox() {
        outboxEventRepository.findBySentFalse().forEach(event -> {
            try {
                OrderCreatedEvent domainEvent = objectMapper.readValue(event.getPayload(), OrderCreatedEvent.class);
                kafkaProducerService.sendOrderCreatedEvent(domainEvent);
                event.setSent(true);
                outboxEventRepository.save(event);
            } catch (Exception e) {
                e.printStackTrace(); // Podes trocar por log
            }
        });
    }
}
