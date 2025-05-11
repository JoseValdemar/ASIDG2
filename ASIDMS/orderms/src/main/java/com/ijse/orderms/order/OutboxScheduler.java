package com.ijse.orderms.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ijse.orderms.event.OrderCreatedEvent;
import com.ijse.orderms.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutboxScheduler {

    @Autowired
    private OutboxEventRepository outboxRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Scheduled(fixedRate = 10000)
    public void processPendingEvents() {
        List<OutboxEvent> events = outboxRepository.findByStatus("PENDING");

        for (OutboxEvent event : events) {
            try {
                OrderCreatedEvent orderCreated = objectMapper.readValue(event.getPayload(), OrderCreatedEvent.class);
                kafkaProducerService.sendOrderCreatedEvent(orderCreated);

                event.setStatus("SENT");
                outboxRepository.save(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
