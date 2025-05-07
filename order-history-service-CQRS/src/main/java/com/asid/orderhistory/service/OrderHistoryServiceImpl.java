package com.asid.orderhistory.service;

import com.asid.orderhistory.entity.OrderHistory;
import com.asid.orderhistory.repository.OrderHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

    @Autowired
    private OrderHistoryRepository repository;

    @Override
    public List<OrderHistory> getAll() {
        return repository.findAll();
    }

    @Override
    public List<OrderHistory> getByDateRange(LocalDateTime start, LocalDateTime end) {
        return repository.findByOrderDateBetween(start, end);
    }
}
