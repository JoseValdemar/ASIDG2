package com.ijse.orderms.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.orderms.order.OrderDetails;
import com.ijse.orderms.order.OrderDetailsService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class OrderDetailsController {
    
    @Autowired
    private OrderDetailsService orderDetailsService;

    @PostMapping("/orderdetails")
    public ResponseEntity<OrderDetails> createOrderDetails(@RequestBody OrderDetails orderDetails){

        OrderDetails orderedDetails = orderDetailsService.createOrderDetails(orderDetails);

        return new ResponseEntity<>(orderedDetails,HttpStatus.CREATED);

    }
    @GetMapping("/orderdetails/{id}")
public ResponseEntity<OrderDetails> getOrderDetailsById(@PathVariable Long id) {
    return orderDetailsService.getOrderDetailsById(id)
            .map(details -> new ResponseEntity<>(details, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
}
}
