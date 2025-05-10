package com.ijse.orderms.event;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderCreatedEvent {
    private Long orderId;
    private Long userId;
    private Long bookId;
    private Long shippingId;
}
