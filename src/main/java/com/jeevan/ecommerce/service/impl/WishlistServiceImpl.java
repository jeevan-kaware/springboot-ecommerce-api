package com.jeevan.ecommerce.service.impl;


import com.jeevan.ecommerce.dto.response.WishlistResponse;
import com.jeevan.ecommerce.entity.Product;
import com.jeevan.ecommerce.entity.User;
import com.jeevan.ecommerce.entity.Wishlist;

import com.jeevan.ecommerce.exception.DuplicateResourceException;
import com.jeevan.ecommerce.exception.ResourceNotFoundException;

import com.jeevan.ecommerce.repository.ProductRepository;
import com.jeevan.ecommerce.repository.UserRepository;
import com.jeevan.ecommerce.repository.WishlistRepository;

import com.jeevan.ecommerce.service.WishlistService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.List;



@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {



    private final WishlistRepository wishlistRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;



    @Override
    public WishlistResponse addToWishlist(
            String email,
            Long productId) {


        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                ));



        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"
                                ));



        boolean exists =
                wishlistRepository
                        .findByUserAndProduct(
                                user,
                                product
                        )
                        .isPresent();



        if(exists){

            throw new DuplicateResourceException(
                    "Product already exists in wishlist"
            );
        }



        Wishlist wishlist =
                Wishlist.builder()
                        .user(user)
                        .product(product)
                        .build();



        Wishlist saved =
                wishlistRepository.save(wishlist);



        return mapToResponse(saved);

    }



    @Override
    public List<WishlistResponse> getWishlist(
            String email) {


        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                ));



        List<Wishlist> wishlistItems =
                wishlistRepository.findByUser(user);



        if(wishlistItems.isEmpty()){

            throw new ResourceNotFoundException(
                    "Wishlist is empty"
            );
        }



        return wishlistItems
                .stream()
                .map(this::mapToResponse)
                .toList();

    }




    @Override
    public String removeFromWishlist(
            String email,
            Long productId) {


        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                ));



        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"
                                ));



        Wishlist wishlist =
                wishlistRepository
                        .findByUserAndProduct(
                                user,
                                product
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found in wishlist"
                                ));



        wishlistRepository.delete(wishlist);



        return "Product removed from wishlist successfully";

    }




    private WishlistResponse mapToResponse(
            Wishlist wishlist){


        Product product =
                wishlist.getProduct();



        return WishlistResponse.builder()

                .productId(product.getId())

                .productName(product.getName())

                .brand(product.getBrand())

                .price(product.getPrice())

                .imageUrl(product.getImageUrl())

                .build();

    }

}