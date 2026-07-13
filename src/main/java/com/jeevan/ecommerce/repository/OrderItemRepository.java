package com.jeevan.ecommerce.repository;


import com.jeevan.ecommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository
        extends JpaRepository<OrderItem, Long> {


}