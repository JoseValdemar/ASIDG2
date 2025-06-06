package com.ijse.orderms.dto;

public class OrderDetailsDTO {
    private Long userId;
    private Long bookId;
    private Long shippingOrderId;
    private int quantity;
    private double subTotal;
    private double unitPrice;
    private double total;

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

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
