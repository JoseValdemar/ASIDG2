package com.asid.orchestrator.order_orchestrator_service.service;

import com.asid.orchestrator.order_orchestrator_service.dto.ItemDTO;
import com.asid.orchestrator.order_orchestrator_service.dto.OrderDTO;
import com.asid.orchestrator.order_orchestrator_service.kafka.KafkaProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderSagaOrchestrator {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private OrderDTO currentOrder;

    public void executeSaga(OrderDTO orderDTO) {
        this.currentOrder = orderDTO;
        try {
            reserveBook(orderDTO);
        } catch (Exception e) {
            rollback("Erro ao iniciar saga: " + e.getMessage());
        }
    }

    public void proceedToCart() {
        kafkaProducerService.sendEvent("saga-cart", "CALCULATE_TOTAL");
    }

    public void proceedToShipping() {
        ShippingRequest payload = new ShippingRequest(currentOrder);
        kafkaProducerService.sendEvent("saga-shipping", "CREATE_SHIPPING");
    }

    public void completeSaga() {
        System.out.println(" SAGA concluída com sucesso.");
    }

    public void rollback(String motivo) {
        System.out.println(" SAGA cancelada. Motivo: " + motivo);
        // Aqui poderás implementar lógica de compensação
    }

    private void reserveBook(OrderDTO dto) {
        kafkaProducerService.sendEvent("saga-book", "RESERVE_BOOK");
    }

    private static class ShippingRequest {
        private Long orderId;
        private Long userId;

        public ShippingRequest(OrderDTO dto) {
            this.orderId = 0L;
            this.userId = dto.getUserId();
        }
    }
}
