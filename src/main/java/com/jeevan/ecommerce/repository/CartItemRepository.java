package com.jeevan.ecommerce.repository;

import com.jeevan.ecommerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository
        extends JpaRepository<CartItem, Long> {


    Optional<CartItem> findByCartIdAndProductId(
            Long cartId,
            Long productId
    );

}