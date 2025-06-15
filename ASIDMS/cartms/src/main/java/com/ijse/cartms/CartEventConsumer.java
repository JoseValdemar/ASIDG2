package com.ijse.cartms;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class CartEventConsumer {

    private final CartHelperService cartHelperService;
    private final CartEventProducer cartEventProducer;

    public CartEventConsumer(CartHelperService cartHelperService, CartEventProducer cartEventProducer) {
        this.cartHelperService = cartHelperService;
        this.cartEventProducer = cartEventProducer;
    }

    @KafkaListener(topics = "saga-cart", groupId = "cartms-group")
    public void handleSagaEvent(String message) {
        System.out.println(" [CARTMS] Evento recebido do orquestrador: " + message);

        try {
            if ("CALCULATE_TOTAL".equals(message)) {
                Long userId = 1L;
                double total = cartHelperService.calcularSubtotalDoCarrinho(userId);
                System.out.println(" Subtotal calculado: " + total);
                cartEventProducer.sendCartStatus("CART_CALCULATED");
            } else if ("CLEAR_CART".equals(message)) {
                Long userId = 1L;
                cartHelperService.resetCart(userId);
                System.out.println(" Carrinho limpo com sucesso");
                cartEventProducer.sendCartStatus("CART_CLEARED");
            } else {
                System.out.println(" Tipo de evento não reconhecido: " + message);
                cartEventProducer.sendCartStatus("CART_FAILED");
            }
        } catch (Exception e) {
            e.printStackTrace();
            cartEventProducer.sendCartStatus("CART_FAILED");
        }
    }
}
