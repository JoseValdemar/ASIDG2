package com.ijse.bookstore.controller;

import com.ijse.bookstore.dto.OrderDetailsDTO;
import com.ijse.bookstore.entity.OrderDetails;
import com.ijse.bookstore.service.OrderDetailsService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderdetails")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @PostMapping
    public ResponseEntity<?> createOrderDetails(@Valid @RequestBody OrderDetailsDTO dto) {
        try {
            OrderDetails saved = orderDetailsService.createOrderDetails(dto);
            return ResponseEntity.status(201).body(saved);
        } catch (Exception ex) {
            // logar se necessário com logger.error("Erro ao criar encomenda", ex);
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long id) {
        return orderDetailsService.getOrderDetailsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/orderdetails/{id}")
    public ResponseEntity<OrderDetails> getOrderDetails(@PathVariable Long id) {
    return orderDetailsService.getOrderDetailsById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
}
}
