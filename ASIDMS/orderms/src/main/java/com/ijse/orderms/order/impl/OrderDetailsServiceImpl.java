package com.ijse.orderms.order.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ijse.orderms.dto.OrderDetailsDTO;
import com.ijse.orderms.event.OrderCreatedEvent;
import com.ijse.orderms.order.OrderDetails;
import com.ijse.orderms.order.OrderDetailsRepository;
import com.ijse.orderms.order.OrderDetailsService;
import com.ijse.orderms.order.OutboxEvent;
import com.ijse.orderms.order.OutboxEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OutboxEventRepository outboxEventRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public OrderDetails createOrder(OrderDetailsDTO dto) {
        // 1. Criar e guardar a encomenda
        OrderDetails order = new OrderDetails();
        order.setUserId(dto.getUserId());
        order.setBookId(dto.getBookId());
        order.setShippingOrderId(dto.getShippingOrderId());
        order.setQuantity(dto.getQuantity());
        order.setSubTotal(dto.getSubTotal());

        OrderDetails saved = orderDetailsRepository.save(order);

        // 2. Criar o evento
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setOrderId(saved.getId());
        event.setUserId(saved.getUserId());
        event.setBookId(saved.getBookId());
        event.setShippingId(saved.getShippingOrderId());
        event.setOrderDate(LocalDateTime.now());
        event.setTotalPrice(saved.getSubTotal());

        try {
            String payload = objectMapper.writeValueAsString(event);

            OutboxEvent outboxEvent = new OutboxEvent();
            outboxEvent.setAggregateType("Order");
            outboxEvent.setAggregateId(saved.getId());
            outboxEvent.setEventType("OrderCreatedEvent");
            outboxEvent.setPayload(payload);
            outboxEvent.setStatus("PENDING");

            outboxEventRepository.save(outboxEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return saved;
    }

    @Override
    public Optional<OrderDetails> getOrderDetailsById(Long id) {
        return orderDetailsRepository.findById(id);
    }
}
