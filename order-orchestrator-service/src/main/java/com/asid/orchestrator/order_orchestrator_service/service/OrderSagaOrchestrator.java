package com.asid.orchestrator.order_orchestrator_service.service;

import com.asid.orchestrator.order_orchestrator_service.dto.OrderDTO;
import com.asid.orchestrator.order_orchestrator_service.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderSagaOrchestrator {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private OrderDTO currentOrder;

    // Etapa de controlo
    private int etapaAtual = 0;

    public void executeSaga(OrderDTO orderDTO) {
        this.currentOrder = orderDTO;
        try {
            etapaAtual = 1;
            kafkaProducerService.sendEvent("saga-order", "CREATE_ORDER");
        } catch (Exception e) {
            rollback("Erro ao iniciar saga: " + e.getMessage());
        }
    }

    public void proceedToBook() {
        try {
            etapaAtual = 2;
            kafkaProducerService.sendEvent("saga-book", "DECREASE_STOCK");
        } catch (Exception e) {
            rollback("Erro ao diminuir stock: " + e.getMessage());
        }
    }

    public void proceedToShipping() {
        try {
            etapaAtual = 3;
            kafkaProducerService.sendEvent("saga-shipping", "CREATE_SHIPPING");
        } catch (Exception e) {
            rollback("Erro ao criar envio: " + e.getMessage());
        }
    }

    public void proceedToCart() {
        try {
            etapaAtual = 4;
            kafkaProducerService.sendEvent("saga-cart", "CLEAR_CART");
        } catch (Exception e) {
            rollback("Erro ao limpar carrinho: " + e.getMessage());
        }
    }

    public void completeSaga() {
        System.out.println(" SAGA concluída com sucesso.");
        etapaAtual = 0;
    }

    public void rollback(String motivo) {
        System.out.println(" SAGA cancelada. Motivo: " + motivo);

        switch (etapaAtual) {
            case 1:
                // Falha logo no início: nao faz nada
                break;

            case 2:
                // Falha ao atualizar stock -> cancelar encomenda
                kafkaProducerService.sendEvent("saga-order", "CANCEL_ORDER");
                break;

            case 3:
                // Falha ao criar envio -> cancelar envio + repor stock + cancelar encomenda
                kafkaProducerService.sendEvent("saga-shipping", "CANCEL_SHIPPING");
                kafkaProducerService.sendEvent("saga-book", "RESTORE_STOCK");
                kafkaProducerService.sendEvent("saga-order", "CANCEL_ORDER");
                break;

            case 4:
                // Falha ao limpar carrinho -> não afeta estado da transação
                System.out.println(" Falha no carrinho. A transação foi bem-sucedida até aqui, mas o carrinho não foi limpo.");
                break;

            default:
                System.out.println(" Estado da etapa desconhecido.");
        }

        // Reset
        etapaAtual = 0;
    }

    // Utilizado para dados mínimos no envio (ex.: shipping)
    private static class ShippingRequest {
        private Long orderId;
        private Long userId;

        public ShippingRequest(OrderDTO dto) {
            this.orderId = 0L; 
            this.userId = dto.getUserId();
        }
    }
}
