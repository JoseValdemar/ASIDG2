package com.asid.orderhistory.service;

import com.asid.orderhistory.entity.OrderHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderHistoryService {
    List<OrderHistory> getAll();
    List<OrderHistory> getByDateRange(LocalDateTime start, LocalDateTime end);
}
