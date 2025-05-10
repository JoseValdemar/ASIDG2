package com.ijse.orderms.dto;

import java.time.LocalDateTime;

public class OrderDetailsDTO {
    private Long userId;
    private Long bookId;
    private Long shippingOrderId; // ADICIONADO
    private int quantity;
    private double subTotal;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public Long getShippingOrderId() { return shippingOrderId; }
    public void setShippingOrderId(Long shippingOrderId) { this.shippingOrderId = shippingOrderId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getSubTotal() { return subTotal; }
    public void setSubTotal(double subTotal) { this.subTotal = subTotal; }
}
