package com.asid.orderhistory.kafka;

import com.asid.orderhistory.client.UserClient;
import com.asid.orderhistory.client.BookClient;
import com.asid.orderhistory.client.ShippingClient;
import com.asid.orderhistory.entity.OrderHistory;
import com.asid.orderhistory.event.OrderCreatedEvent;
import com.asid.orderhistory.repository.OrderHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KafkaConsumerService {

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private BookClient bookClient;

    @Autowired
    private ShippingClient shippingClient;

    @KafkaListener(topics = "order-created", groupId = "order-history-group")
    public void consume(OrderCreatedEvent event) {
        // Verificar duplicados
        if (orderHistoryRepository.existsById(event.getOrderId())) {
            return;
        }

        // Obter dados adicionais via REST
        String userEmail = userClient.getUserEmail(event.getUserId()); // já é síncrono
        String bookTitle = bookClient.getBookTitle(event.getBookId()).block();
        String shippingAddress = shippingClient.getShippingAddress(event.getShippingId()).block();
        LocalDateTime shippingDate = shippingClient.getShippingDate(event.getShippingId()).block();

        // Construir e guardar o histórico
        OrderHistory order = new OrderHistory();
        order.setOrderId(event.getOrderId());
        order.setUserId(event.getUserId());
        order.setUserEmail(userEmail);
        order.setOrderDate(event.getOrderDate());
        order.setTotalPrice(event.getTotalPrice());
        order.setShippingAddress(shippingAddress);
        order.setShippingDate(shippingDate);
        order.setBookTitles(bookTitle);

        orderHistoryRepository.save(order);
    }
}
