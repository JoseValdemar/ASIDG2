package com.ijse.shippingms.shipping;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ijse.shippingms.shipping.ShippingOrder;

@Repository
public interface ShippingOrderRepository extends JpaRepository<ShippingOrder,Long>{
 
}
