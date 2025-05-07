package com.ijse.shippingms.shipping;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ijse.shippingms.shipping.ShippingOrder;



@Service
public interface ShippingOrderService {
    
    ShippingOrder createShippingOrder(ShippingOrder shippingOrder);

    List<ShippingOrder> getAllShippingOrders();
}
