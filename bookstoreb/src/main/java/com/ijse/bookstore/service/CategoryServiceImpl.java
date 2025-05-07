package com.ijse.bookstore.service;

import com.ijse.bookstore.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    // Implementação dos métodos do CategoryService será aqui

}
