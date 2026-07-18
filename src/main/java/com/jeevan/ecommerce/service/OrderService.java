package com.jeevan.ecommerce.service;


import com.jeevan.ecommerce.dto.response.OrderResponse;


import java.util.List;


public interface OrderService {


    OrderResponse placeOrder(String email);


    List<OrderResponse> getMyOrders(String email);


    OrderResponse getOrderById(String email,Long orderId);


}