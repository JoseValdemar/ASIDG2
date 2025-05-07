package com.ijse.cartms.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ijse.cartms.cart.CartItem;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    
    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE cart_item AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

    List<CartItem> findByUserId(Long userId);
}
