package com.asid.orderhistory.repository;

import com.asid.orderhistory.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    @Query("SELECT o FROM OrderHistory o WHERE o.orderDate BETWEEN :start AND :end")
    List<OrderHistory> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);
}
