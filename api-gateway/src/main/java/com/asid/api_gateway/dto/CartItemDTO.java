package com.asid.api_gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartItemDTO {

    private int quantity;

    @JsonProperty("unit_price")
    private Double unitPrice;

    @JsonProperty("sub_total")
    private Double subTotal;

    @JsonProperty("book_id")
    private Long bookId;

    @JsonProperty("user_id")
    private Long userId;
}

