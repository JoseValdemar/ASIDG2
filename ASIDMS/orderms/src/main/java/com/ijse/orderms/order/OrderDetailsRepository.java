package com.ijse.orderms.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ijse.orderms.order.OrderDetails;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long>{
    
}
