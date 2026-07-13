package com.jeevan.ecommerce.service;

import com.jeevan.ecommerce.dto.request.AddToCartRequest;
import com.jeevan.ecommerce.dto.response.CartResponse;

public interface CartService {


    CartResponse addToCart(
            String email,
            AddToCartRequest request
    );


    CartResponse getCart(
            String email
    );


    CartResponse updateQuantity(
            Long cartItemId,
            Integer quantity
    );


    String removeItem(
            Long cartItemId
    );


    String clearCart(
            String email
    );

}