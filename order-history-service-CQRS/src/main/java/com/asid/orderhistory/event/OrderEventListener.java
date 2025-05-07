package com.asid.orderhistory.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {
    @EventListener
    public void handle(OrderCreatedEvent event) {
        System.out.println("[✓] Evento consumido: " + event.getOrderId());
    }
}