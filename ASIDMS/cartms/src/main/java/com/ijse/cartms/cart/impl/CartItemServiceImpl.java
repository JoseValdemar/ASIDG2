package com.ijse.cartms.cart.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijse.cartms.cart.CartItem;
import com.ijse.cartms.cart.CartItemRepository;
import com.ijse.cartms.cart.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService{

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartItem createCartItem(CartItem cartItem){

        return cartItemRepository.save(cartItem);


    }

   public List<CartItem> getAllCartitem(){

        return cartItemRepository.findAll();


    }

    public CartItem getCartItemById(Long id){

        return cartItemRepository.findById(id).orElse(null);
    }

    public CartItem patchCartQuantity(Long id, CartItem cartItem){
        CartItem existItem = cartItemRepository.findById(id).orElse(null);
    
        if (existItem != null) {
            
            existItem.setQuantity(cartItem.getQuantity());
            cartItemRepository.save(existItem);
    
            return existItem;
        } else { 
            return null;
        }
    }
    

    public CartItem patchCartSubTotal(Long id, CartItem cartItem){
        CartItem existItem = cartItemRepository.findById(id).orElse(null);
    
        if (existItem != null) {
            
            existItem.setSubTotal(cartItem.getSubTotal());
            cartItemRepository.save(existItem);
    
            return existItem;
        } else { 
            return null;
        }
    }


    public CartItem deleteCartItyItemById(Long id){

        CartItem existItem = cartItemRepository.findById(id).orElse(null);

        if(existItem !=null){

            cartItemRepository.delete(existItem);
        }
        return null;
    }


    public void clearCart(){

        cartItemRepository.deleteAll();
        
    }

    public void resetAutoIncrement() {
        cartItemRepository.resetAutoIncrement();
    }

    public List<CartItem> getCartItemsByUserId(Long userId) {

        return cartItemRepository.findByUserId(userId);
        
    }
}
