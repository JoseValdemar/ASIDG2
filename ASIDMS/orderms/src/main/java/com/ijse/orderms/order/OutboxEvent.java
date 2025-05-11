package com.ijse.orderms.order;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_event")
@Data
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aggregateType;
    private Long aggregateId;
    private String eventType;

    @Lob
    private String payload;

    private String status;

    private LocalDateTime createdAt = LocalDateTime.now();
}
