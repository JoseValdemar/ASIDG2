package com.ijse.orderms.order;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String userEmail;
    private Long bookId;
    private String bookTitle;

    private Long shippingOrderId; // novo campo para associar ao envio

    private String shippingAddress;
    private LocalDateTime shippingDate;

    private int quantity;
    private double subTotal;
}
