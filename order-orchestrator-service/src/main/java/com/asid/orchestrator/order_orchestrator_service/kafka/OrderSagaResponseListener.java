package com.asid.orchestrator.order_orchestrator_service.kafka;

import com.asid.orchestrator.order_orchestrator_service.service.OrderSagaOrchestrator;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderSagaResponseListener {

    private final OrderSagaOrchestrator orchestrator;

    public OrderSagaResponseListener(OrderSagaOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @KafkaListener(topics = "saga-orchestrator", groupId = "ordersaga-group")
    public void listen(String message) {
        System.out.println(" [ORCHESTRATOR] Resposta recebida: " + message);

        switch (message) {
            case "ORDER_CREATED" -> orchestrator.proceedToBook();
            case "ORDER_FAILED" -> orchestrator.rollback("erro ao criar encomenda");
            
            case "BOOK_RESERVED" -> orchestrator.proceedToCart();
            case "BOOK_FAILED" -> orchestrator.rollback("Erro ao reservar livro");

            case "CART_CALCULATED" -> orchestrator.proceedToShipping();
            case "CART_FAILED" -> orchestrator.rollback("Erro no carrinho");

            case "SHIPPING_CREATED" -> orchestrator.completeSaga();
            case "SHIPPING_FAILED" -> orchestrator.rollback("Erro no envio");

            default -> System.out.println(" Evento desconhecido: " + message);
        }
    }
}
