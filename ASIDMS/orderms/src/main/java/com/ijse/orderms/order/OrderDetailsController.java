// em: order/OrderDetailsController.java
package com.ijse.orderms.order;

import com.ijse.orderms.dto.OrderDetailsDTO;
import com.ijse.orderms.order.OrderDetails;
import com.ijse.orderms.order.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderdetails")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @PostMapping
    public ResponseEntity<OrderDetails> create(@RequestBody OrderDetailsDTO dto) {
        return ResponseEntity.status(201).body(orderDetailsService.createOrder(dto));
    }
}
