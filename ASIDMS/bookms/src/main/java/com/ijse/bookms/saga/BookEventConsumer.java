package com.asid.bookms.saga;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookEventConsumer {

    private final BookEventProducer bookEventProducer;

    public BookEventConsumer(BookEventProducer bookEventProducer) {
        this.bookEventProducer = bookEventProducer;
    }

    @KafkaListener(topics = "saga-book", groupId = "bookms-group")
    public void listen(String message) {
        System.out.println("📥 [BOOKMS] Evento recebido do orquestrador: " + message);

        // Simular lógica de reserva de stock
        boolean sucesso = simularReservaLivro();

        // Enviar resposta de volta ao orquestrador
        if (sucesso) {
            bookEventProducer.sendEvent("BOOK_RESERVED");
        } else {
            bookEventProducer.sendEvent("BOOK_FAILED");
        }
    }

    private boolean simularReservaLivro() {
        // Aqui podes integrar com o teu serviço real de reserva de stock
        return true; // Simula sucesso
    }
}
