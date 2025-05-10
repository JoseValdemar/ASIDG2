package com.asid.orchestrator.order_orchestrator_service.service;

import com.asid.orchestrator.order_orchestrator_service.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.asid.orchestrator.order_orchestrator_service.kafka.KafkaProducerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.asid.orchestrator.order_orchestrator_service.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@Service
public class OrderSagaOrchestrator {

    @Autowired
    private KafkaProducerService kafkaProducerService;


    public void executeSaga(OrderDTO orderDTO) {
    try {
            createOrder(orderDTO);
            updateStock(orderDTO.getItems());
            registerShipping(orderDTO);
            clearCart(orderDTO.getUserId());
        } catch (Exception e) {
            rollback(orderDTO);
            throw new RuntimeException("Erro na SAGA (envio de eventos): " + e.getMessage());
        }
    }


    private void createOrder(OrderDTO dto) {
        kafkaProducerService.sendEvent("saga.order.create", serialize(dto));
    }


    private void updateStock(List<ItemDTO> items) {
        kafkaProducerService.sendEvent("saga.book.decreaseStock", serialize(items));
    }


    private void registerShipping(OrderDTO dto) {
        ShippingRequest payload = new ShippingRequest(dto);
        kafkaProducerService.sendEvent("saga.shipping.create", serialize(payload));
    }



    private void clearCart(Long userId) {
        kafkaProducerService.sendEvent("saga.cart.clear", String.valueOf(userId));
    }


    private void rollback(OrderDTO dto) {
        // (futuramente) enviar evento compensatório para cancelar encomenda
    }


    private String serialize(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao serializar objeto: " + e.getMessage());
        }
    }


    private static class ShippingRequest {
        private Long orderId;
        private Long userId;

        public ShippingRequest(OrderDTO dto) {
            this.orderId = 0L; // ainda não tens o ID da encomenda criado
            this.userId = dto.getUserId();
        }
    
        public Long getOrderId() {
            return orderId;
        }
    
        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }
    
        public Long getUserId() {
            return userId;
        }
    
        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }
    
}
