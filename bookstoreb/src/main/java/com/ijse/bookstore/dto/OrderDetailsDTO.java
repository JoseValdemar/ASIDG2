package com.ijse.bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderDetailsDTO {

    @NotNull(message = "O ID do utilizador é obrigatório")
    private Long userId;

    @NotNull(message = "O ID do livro é obrigatório")
    private Long bookId;

    @NotNull(message = "O ID da encomenda de envio é obrigatório")
    private Long shippingOrderId;

    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    private int quantity;

    @Positive(message = "O subtotal deve ser maior que 0")
    private double subTotal;

    public OrderDetailsDTO() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getShippingOrderId() {
        return shippingOrderId;
    }

    public void setShippingOrderId(Long shippingOrderId) {
        this.shippingOrderId = shippingOrderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
