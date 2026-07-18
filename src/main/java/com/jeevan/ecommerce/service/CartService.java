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

            String email,

            Long cartItemId,

            Integer quantity
    );


    String removeItem(

            String email,

            Long cartItemId
    );

    String clearCart(
            String email
    );

}