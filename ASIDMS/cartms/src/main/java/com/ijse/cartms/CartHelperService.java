package com.ijse.cartms;

import com.ijse.cartms.cart.CartItem;
import com.ijse.cartms.cart.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartHelperService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public void resetCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }

    public double calcularSubtotalDoCarrinho(Long userId) {
        List<CartItem> items = cartItemRepository.findByUserId(userId);
        return items.stream().mapToDouble(CartItem::getSubTotal).sum();
    }
}
