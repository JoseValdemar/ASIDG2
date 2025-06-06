package com.ijse.shippingms;

import com.ijse.shippingms.shipping.ShippingOrder;
import com.ijse.shippingms.shipping.ShippingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ShippingEventConsumer {

    private final ShippingEventProducer shippingEventProducer;
    private final ShippingOrderService shippingOrderService;

    @Autowired
    public ShippingEventConsumer(ShippingOrderService shippingOrderService, ShippingEventProducer shippingEventProducer) {
        this.shippingOrderService = shippingOrderService;
        this.shippingEventProducer = shippingEventProducer;
    }

    @KafkaListener(topics = "saga-shipping", groupId = "shippingms-group")
    public void handleSagaEvent(String message) {
        System.out.println("📥 [SHIPPINGMS] Evento recebido do orquestrador: " + message);

        try {
            if ("CREATE_SHIPPING".equals(message)) {
                Long userId = 1L;

                ShippingOrder shipping = new ShippingOrder();
                //shipping.setUserId(userId);
                shipping.setCity("Braga");
                //shipping.setPostalCode("4710-000");
                shipping.setAddress("Rua Exemplo 123");
                shipping.setEmail("user@example.com");
                shipping.setFirstName("João");
                shipping.setLastName("Silva");
                shipping.setShippingDate(java.time.LocalDateTime.now());
                //shipping.setStatus("pendente");

                shippingOrderService.createShippingOrder(shipping);


                System.out.println("✅ Envio registado com sucesso.");
                shippingEventProducer.sendShippingStatus("SHIPPING_CREATED");
            } else {
                System.out.println("⚠️ Tipo de evento não reconhecido.");
                shippingEventProducer.sendShippingStatus("SHIPPING_FAILED");
            }
        } catch (Exception e) {
            e.printStackTrace();
            shippingEventProducer.sendShippingStatus("SHIPPING_FAILED");
        }
    }
}
