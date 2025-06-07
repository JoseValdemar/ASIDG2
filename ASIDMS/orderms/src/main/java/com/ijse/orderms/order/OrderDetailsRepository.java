package com.ijse.orderms.order;

import java.util.Optional;
import com.ijse.orderms.order.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    Optional<OrderDetails> findTopByOrderByIdDesc();

}
