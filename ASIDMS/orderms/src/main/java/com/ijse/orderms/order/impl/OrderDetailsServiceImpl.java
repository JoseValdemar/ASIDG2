package com.ijse.orderms.order.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijse.orderms.order.OrderDetails;
import com.ijse.orderms.order.OrderDetailsRepository;
import com.ijse.orderms.order.OrderDetailsService;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService{
    
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;


    public OrderDetails createOrderDetails(OrderDetails orderDetails){

        return orderDetailsRepository.save(orderDetails);

    }
    @Override
public Optional<OrderDetails> getOrderDetailsById(Long id) {
    return orderDetailsRepository.findById(id);
}
}
