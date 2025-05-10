package com.asid.orchestrator.order_orchestrator_service.controller;

import com.asid.orchestrator.order_orchestrator_service.dto.OrderDTO;
import com.asid.orchestrator.order_orchestrator_service.service.OrderSagaOrchestrator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderdetails")
@CrossOrigin(origins = "*")
public class OrderOrchestratorController {

    private final OrderSagaOrchestrator orchestrator;

    public OrderOrchestratorController(OrderSagaOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO dto) {
        orchestrator.executeSaga(dto);
        return ResponseEntity.ok("SAGA executada com sucesso.");
    }
}
