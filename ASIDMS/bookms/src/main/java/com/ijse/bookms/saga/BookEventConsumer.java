package com.ijse.bookms.saga;

import com.ijse.bookms.book.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ijse.bookms.book.BookService;
import com.ijse.bookms.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookEventConsumer {

    @Autowired
    private BookService bookService;

    private final BookEventProducer bookEventProducer;

    public BookEventConsumer(BookEventProducer bookEventProducer) {
        this.bookEventProducer = bookEventProducer;
    }

    @KafkaListener(topics = "saga-book", groupId = "bookms-group")
    public void listen(String message) {
        System.out.println("📥 [BOOKMS] Evento recebido do orquestrador: " + message);

        // ⚠️ Simulação provisória — hardcoded bookId e quantidade
        boolean sucesso = simularReservaLivro(1L, 1);

        if (sucesso) {
            bookEventProducer.sendEvent("BOOK_RESERVED");
        } else {
            bookEventProducer.sendEvent("BOOK_FAILED");
        }
    }

    private boolean simularReservaLivro(Long bookId, int quantidade) {
        try {
            Book book = bookService.getBookById(bookId);
            if (book != null && book.getStock() >= quantidade) {
                book.setStock(book.getStock() - quantidade);
                bookService.updateBook(book);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
