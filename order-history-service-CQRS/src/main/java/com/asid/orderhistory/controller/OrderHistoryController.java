package com.asid.orderhistory.controller;

import com.asid.orderhistory.entity.OrderHistory;
import com.asid.orderhistory.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/history")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderHistoryController {

    @Autowired
    private OrderHistoryService orderHistoryService;

    @GetMapping("/all")
    public ResponseEntity<List<OrderHistory>> getAll() {
        return ResponseEntity.ok(orderHistoryService.getAll());
    }

    @GetMapping
    public ResponseEntity<List<OrderHistory>> getByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        List<OrderHistory> orders = orderHistoryService.getByDateRange(start, end);
        return ResponseEntity.ok(orders);
    }
}
