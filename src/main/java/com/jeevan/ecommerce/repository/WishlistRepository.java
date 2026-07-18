package com.jeevan.ecommerce.repository;


import com.jeevan.ecommerce.entity.Wishlist;
import com.jeevan.ecommerce.entity.Product;
import com.jeevan.ecommerce.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface WishlistRepository
        extends JpaRepository<Wishlist, Long> {



    List<Wishlist> findByUser(User user);



    Optional<Wishlist> findByUserAndProduct(
            User user,
            Product product
    );



    void deleteByUserAndProduct(
            User user,
            Product product
    );

}