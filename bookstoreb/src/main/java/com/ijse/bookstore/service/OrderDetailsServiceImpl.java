package com.ijse.bookstore.service;

import com.ijse.bookstore.dto.OrderDetailsDTO;
import com.ijse.bookstore.entity.Book;
import com.ijse.bookstore.entity.OrderDetails;
import com.ijse.bookstore.entity.ShippingOrder;
import com.ijse.bookstore.entity.User;
import com.ijse.bookstore.event.OrderCreatedEvent;
import com.ijse.bookstore.kafka.KafkaProducerService;
import com.ijse.bookstore.repository.BookRepository;
import com.ijse.bookstore.repository.OrderDetailsRepository;
import com.ijse.bookstore.repository.ShippingOrderRepository;
import com.ijse.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ShippingOrderRepository shippingOrderRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Override
    public OrderDetails createOrderDetails(OrderDetailsDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Utilizador não encontrado com ID: " + dto.getUserId()));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Livro não encontrado com ID: " + dto.getBookId()));

        ShippingOrder shippingOrder = shippingOrderRepository.findById(dto.getShippingOrderId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Informação de envio não encontrada com ID: " + dto.getShippingOrderId()));

        // Validação explícita para evitar nulls em campos primitivos
        if (book.getQuantity() == 0 && dto.getQuantity() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade do livro não pode ser 0");
        }

        if (book.getPrice() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Preço do livro inválido");
        }

        OrderDetails order = new OrderDetails();
        order.setUser(user);
        order.setBook(book);
        order.setShippingOrder(shippingOrder);
        order.setQuantity(dto.getQuantity());
        order.setSubTotal(dto.getSubTotal());

        OrderDetails saved = orderDetailsRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setOrderId(saved.getId());
        event.setUserId(user.getId());
        event.setUserEmail(user.getEmail());
        event.setOrderDate(LocalDateTime.now());
        event.setTotalPrice(saved.getSubTotal());
        event.setBookTitles(List.of(book.getTitle()));
        event.setShippingAddress(shippingOrder.getAddress());
        event.setShippingDate(shippingOrder.getShippingDate());

        kafkaProducerService.sendOrderCreatedEvent(event);

        return saved;
    }

    @Override
    public Optional<OrderDetails> getOrderDetailsById(Long id) {
        return orderDetailsRepository.findById(id);
    }
}
