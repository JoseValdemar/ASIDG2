package com.ijse.orderms.order.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ijse.orderms.client.BookClient;
import com.ijse.orderms.client.ShippingClient;
import com.ijse.orderms.client.UserClient;
import com.ijse.orderms.dto.OrderDetailsDTO;
import com.ijse.orderms.event.OrderCreatedEvent;
import com.ijse.orderms.order.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private UserClient userClient;

    @Autowired
    private BookClient bookClient;

    @Autowired
    private ShippingClient shippingClient;

    @Transactional
    @Override
    public OrderDetails createOrder(OrderDetailsDTO dto) {
        // 1. Verificações via REST
        if (!userClient.userExists(dto.getUserId())) {
            throw new IllegalArgumentException("Utilizador não encontrado com ID: " + dto.getUserId());
        }

        if (!bookClient.bookExists(dto.getBookId())) {
            throw new IllegalArgumentException("Livro não encontrado com ID: " + dto.getBookId());
        }

        if (!shippingClient.shippingExists(dto.getShippingOrderId())) {
            throw new IllegalArgumentException("Envio não encontrado com ID: " + dto.getShippingOrderId());
        }

        // 2. Criar e guardar a encomenda
        OrderDetails order = new OrderDetails();
        order.setUserId(dto.getUserId());
        order.setBookId(dto.getBookId());
        order.setShippingOrderId(dto.getShippingOrderId());
        order.setQuantity(dto.getQuantity());
        order.setUnitPrice(dto.getUnitPrice());
        order.setSubTotal(dto.getSubTotal());
        order.setTotal(dto.getTotal());
        order.setOrderDate(LocalDateTime.now());

        OrderDetails saved = orderDetailsRepository.save(order);

        // 3. Criar e guardar o evento na Outbox
        try {
            OrderCreatedEvent event = new OrderCreatedEvent();
            event.setOrderId(saved.getId());
            event.setUserId(saved.getUserId());
            event.setBookId(saved.getBookId());
            event.setShippingId(saved.getShippingOrderId());
            event.setOrderDate(saved.getOrderDate());
            event.setTotalPrice(saved.getTotal());

            String payload = objectMapper.writeValueAsString(event);

            OutboxEvent outboxEvent = new OutboxEvent();
            outboxEvent.setAggregateType("Order");
            outboxEvent.setAggregateId(saved.getId());
            outboxEvent.setEventType("OrderCreatedEvent");
            outboxEvent.setPayload(payload);
            outboxEvent.setStatus("PENDING");

            outboxEventRepository.save(outboxEvent);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar evento de encomenda: " + e.getMessage(), e);
        }

        return saved;
    }

    @Override
    public Optional<OrderDetails> getOrderDetailsById(Long id) {
        return orderDetailsRepository.findById(id);
    }
}
