package com.ijse.shippingms.shipping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    private final ShippingOrderRepository shippingRepository;
    private final ShippingOrderService shippingService;

    @Autowired
    public ShippingController(ShippingOrderRepository shippingRepository,
                              ShippingOrderService shippingService) {
        this.shippingRepository = shippingRepository;
        this.shippingService = shippingService;
    }

    //Criar envio
    @PostMapping
    public ResponseEntity<ShippingOrder> createShipping(@RequestBody ShippingOrder shippingOrder) {
        ShippingOrder created = shippingService.createShippingOrder(shippingOrder);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //Obter envio por ID
    @GetMapping("/{id}")
    public ResponseEntity<ShippingOrder> getShippingById(@PathVariable Long id) {
        return shippingRepository.findById(id)
                .map(shipping -> new ResponseEntity<>(shipping, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Verificar se envio existe
    @GetMapping("/exists/{id}")
    public ResponseEntity<Void> checkShippingExists(@PathVariable Long id) {
        if (shippingService.existsById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Listar todos os envios
    @GetMapping
    public ResponseEntity<List<ShippingOrder>> getAllShippings() {
        List<ShippingOrder> all = shippingRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}

