package com.ijse.cartms.cart;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ijse.cartms.cart.Cart;

@Service
public interface CartService {

    Cart createCart(Cart cart);
    List<Cart> getAllCart();
    Cart getCartIdByUserId(Long userId);
}
