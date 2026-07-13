package com.jeevan.ecommerce.service;

import com.jeevan.ecommerce.dto.request.ChangePasswordRequest;
import com.jeevan.ecommerce.dto.response.OrderResponse;
import com.jeevan.ecommerce.dto.response.UserProfileResponse;
import com.jeevan.ecommerce.entity.User;
import com.jeevan.ecommerce.enums.OrderStatus;

import java.util.List;

public interface AdminService {

    List<UserProfileResponse> getAllUsers();

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id);

    String updateOrderStatus(Long id, OrderStatus status);
}