package com.ijse.orderms.order;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ijse.orderms.order.OrderDetails;

@Service
public interface OrderDetailsService {
    OrderDetails createOrderDetails(OrderDetails orderDetails);
    Optional<OrderDetails> getOrderDetailsById(Long id);
}
