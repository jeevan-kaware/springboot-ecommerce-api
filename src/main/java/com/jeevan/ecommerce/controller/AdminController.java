package com.jeevan.ecommerce.controller;

import com.jeevan.ecommerce.dto.request.ChangePasswordRequest;
import com.jeevan.ecommerce.dto.response.OrderResponse;
import com.jeevan.ecommerce.dto.response.UserProfileResponse;
import com.jeevan.ecommerce.entity.User;
import com.jeevan.ecommerce.enums.OrderStatus;
import com.jeevan.ecommerce.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @GetMapping("/users")
    public List<UserProfileResponse> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/orders")
    public List<OrderResponse> getAllOrders() {
        return adminService.getAllOrders();
    }

    @GetMapping("/orders/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return adminService.getOrderById(id);
    }

    @PutMapping("/orders/{id}/status")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {

        return adminService.updateOrderStatus(id, status);
    }

}