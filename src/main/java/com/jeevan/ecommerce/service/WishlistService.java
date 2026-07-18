package com.jeevan.ecommerce.service;


import com.jeevan.ecommerce.dto.response.WishlistResponse;

import java.util.List;


public interface WishlistService {


    WishlistResponse addToWishlist(
            String email,
            Long productId
    );



    List<WishlistResponse> getWishlist(
            String email
    );



    String removeFromWishlist(
            String email,
            Long productId
    );

}