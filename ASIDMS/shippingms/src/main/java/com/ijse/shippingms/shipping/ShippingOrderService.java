package com.ijse.shippingms.shipping;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ShippingOrderService {
    
    ShippingOrder createShippingOrder(ShippingOrder shippingOrder);

    List<ShippingOrder> getAllShippingOrders();

    boolean existsById(Long id); // ✅ Adicionado para verificação
}
