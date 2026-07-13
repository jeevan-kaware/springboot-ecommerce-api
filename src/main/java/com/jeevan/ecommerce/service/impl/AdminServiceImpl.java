package com.jeevan.ecommerce.service.impl;

import com.jeevan.ecommerce.dto.response.OrderItemResponse;
import com.jeevan.ecommerce.dto.response.OrderResponse;
import com.jeevan.ecommerce.dto.response.UserProfileResponse;
import com.jeevan.ecommerce.entity.Order;
import com.jeevan.ecommerce.entity.OrderItem;
import com.jeevan.ecommerce.entity.User;
import com.jeevan.ecommerce.enums.OrderStatus;
import com.jeevan.ecommerce.exception.ResourceNotFoundException;
import com.jeevan.ecommerce.repository.OrderRepository;
import com.jeevan.ecommerce.repository.UserRepository;
import com.jeevan.ecommerce.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<UserProfileResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> UserProfileResponse.builder()
                        .id(user.getId())
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .build())
                .toList();
    }

    @Override
    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        return mapToResponse(order);
    }

    @Override
    public String updateOrderStatus(Long id,
                                    OrderStatus status) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        order.setStatus(status);

        orderRepository.save(order);

        return "Order Status Updated Successfully";
    }

    private OrderResponse mapToResponse(Order order) {

        List<OrderItemResponse> items = new ArrayList<>();

        for (OrderItem item : order.getOrderItems()) {

            items.add(
                    OrderItemResponse.builder()
                            .productId(item.getProduct().getId())
                            .productName(item.getProduct().getName())
                            .quantity(item.getQuantity())
                            .price(item.getPrice())
                            .build()
            );
        }

        return OrderResponse.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }
}