package com.ijse.orderms.order.impl;

import com.ijse.orderms.dto.OrderDetailsDTO;
import com.ijse.orderms.order.OrderDetails;
import com.ijse.orderms.event.OrderCreatedEvent;
import com.ijse.orderms.kafka.KafkaProducerService;
import com.ijse.orderms.order.OrderDetailsRepository;
import com.ijse.orderms.order.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Override
    public OrderDetails createOrder(OrderDetailsDTO dto) {
        OrderDetails order = new OrderDetails();
        order.setUserId(dto.getUserId());
        order.setBookId(dto.getBookId());
        order.setShippingId(dto.getShippingOrderId()); // novo campo
        order.setQuantity(dto.getQuantity());
        order.setSubTotal(dto.getSubTotal());

        OrderDetails saved = orderDetailsRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setOrderId(saved.getId());
        event.setUserId(saved.getUserId());
        event.setBookId(saved.getBookId());
        event.setShippingId(saved.getShippingId()); // novo campo

        kafkaProducerService.sendOrderCreatedEvent(event);

        return saved;
    }

    @Override
    public Optional<OrderDetails> getOrderDetailsById(Long id) {
        return orderDetailsRepository.findById(id);
    }
}
