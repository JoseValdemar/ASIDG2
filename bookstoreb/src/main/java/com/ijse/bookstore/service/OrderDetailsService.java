package com.ijse.bookstore.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ijse.bookstore.dto.OrderDetailsDTO;
import com.ijse.bookstore.entity.OrderDetails;

@Service
public interface OrderDetailsService {
    OrderDetails createOrderDetails(OrderDetailsDTO dto);
    Optional<OrderDetails> getOrderDetailsById(Long id);
}
