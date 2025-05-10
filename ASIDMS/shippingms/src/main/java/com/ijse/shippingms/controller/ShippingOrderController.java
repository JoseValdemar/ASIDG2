package com.ijse.shippingms.controller;

import com.ijse.shippingms.shipping.ShippingOrder;
import com.ijse.shippingms.shipping.ShippingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipping-orders")
public class ShippingOrderController {

    @Autowired
    private ShippingOrderRepository shippingOrderRepository;

    @GetMapping("/{id}")
    public ResponseEntity<ShippingOrder> getShippingOrderById(@PathVariable Long id) {
        return shippingOrderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
