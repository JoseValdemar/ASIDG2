package com.ijse.shippingms.shipping.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijse.shippingms.shipping.ShippingOrderService;
import com.ijse.shippingms.shipping.ShippingOrder;
import com.ijse.shippingms.shipping.ShippingOrderRepository;

@Service
public class ShippingOrderServiceImpl implements ShippingOrderService{
    
    @Autowired
    private ShippingOrderRepository shippingOrderRepository;


    @Override
    public ShippingOrder createShippingOrder(ShippingOrder shippingOrder){

        return shippingOrderRepository.save(shippingOrder);

    }

    public List<ShippingOrder> getAllShippingOrders(){

        
        return shippingOrderRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return shippingOrderRepository.existsById(id);
    }

}
