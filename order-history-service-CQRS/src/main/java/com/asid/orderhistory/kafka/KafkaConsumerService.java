package com.asid.orderhistory.kafka;

import com.asid.orderhistory.event.OrderCreatedEvent;
import com.asid.orderhistory.entity.OrderHistory;
import com.asid.orderhistory.repository.OrderHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @KafkaListener(topics = "order-created", groupId = "order-history-group")
    public void consume(OrderCreatedEvent event) {
        OrderHistory order = new OrderHistory();
        order.setOrderId(event.getOrderId());
        order.setUserId(event.getUserId());
        order.setUserEmail(event.getUserEmail());
        order.setOrderDate(event.getOrderDate());
        order.setTotalPrice(event.getTotalPrice());
        order.setShippingAddress(event.getShippingAddress());
        order.setShippingDate(event.getShippingDate());
        order.setBookTitles(String.join(", ", event.getBookTitles()));

        orderHistoryRepository.save(order);
    }
}

