package com.ijse.cartms.cart;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ijse.cartms.cart.CartItem;

@Service
public interface CartItemService {
    List<CartItem> getAllCartitem();
    CartItem createCartItem(CartItem cartItem);
    CartItem getCartItemById(Long id);
    CartItem patchCartQuantity(Long id , CartItem cartItem);
    CartItem patchCartSubTotal(Long id , CartItem cartItem);
    CartItem deleteCartItyItemById(Long id);
    void clearCart();
    void resetAutoIncrement();
    List<CartItem> getCartItemsByUserId(Long userId);
}
