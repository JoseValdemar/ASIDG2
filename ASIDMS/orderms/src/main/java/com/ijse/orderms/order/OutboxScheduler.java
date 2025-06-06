package com.ijse.orderms.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ijse.orderms.event.OrderCreatedEvent;
import com.ijse.orderms.kafka.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class OutboxScheduler {

    private static final Logger log = LoggerFactory.getLogger(OutboxScheduler.class);

    @Autowired
    private OutboxEventRepository outboxRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Scheduled(fixedRate = 10000) // Executa a cada 10 segundos
    @Transactional
    public void processPendingEvents() {
        List<OutboxEvent> events = outboxRepository.findByStatus("PENDING");

        for (OutboxEvent event : events) {
            try {
                OrderCreatedEvent orderCreated = objectMapper.readValue(event.getPayload(), OrderCreatedEvent.class);

                kafkaProducerService.sendOrderCreatedEvent(orderCreated);

                event.setStatus("SENT");
                outboxRepository.save(event);

                log.info("Evento enviado com sucesso: orderId = {}", orderCreated.getOrderId());
            } catch (Exception e) {
                log.error("Erro ao processar evento outbox com ID {}: {}", event.getId(), e.getMessage(), e);
            }
        }
    }
}
