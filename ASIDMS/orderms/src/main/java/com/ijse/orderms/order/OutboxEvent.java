package com.ijse.orderms.order;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "outbox_event")
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aggregateType;
    private Long aggregateId;
    private String eventType;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private Boolean sent = false;
    private LocalDateTime createdAt = LocalDateTime.now();
}
