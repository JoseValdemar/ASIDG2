package com.ijse.orderms.order;

import com.ijse.orderms.dto.OrderDetailsDTO;
import com.ijse.orderms.order.OrderDetails;

import java.util.Optional;

public interface OrderDetailsService {
    OrderDetails createOrder(OrderDetailsDTO dto);
    Optional<OrderDetails> getOrderDetailsById(Long id);
    void cancelLatestOrder();

}
