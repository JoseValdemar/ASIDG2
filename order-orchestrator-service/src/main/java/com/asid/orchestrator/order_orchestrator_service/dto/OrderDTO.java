package com.asid.orchestrator.order_orchestrator_service.dto;

import java.util.List;

public class OrderDTO {
    private Long userId;
    private List<ItemDTO> items;

    // Getters e Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public List<ItemDTO> getItems() { return items; }
    public void setItems(List<ItemDTO> items) { this.items = items; }
}

