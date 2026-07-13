package com.jeevan.ecommerce.repository;


import com.jeevan.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findByUserId(Long userId);


}