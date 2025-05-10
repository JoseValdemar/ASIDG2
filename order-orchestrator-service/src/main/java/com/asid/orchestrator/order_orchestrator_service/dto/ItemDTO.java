package com.asid.orchestrator.order_orchestrator_service.dto;

public class ItemDTO {
    private Long bookId;
    private int quantity;

    // Getters e Setters
    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
