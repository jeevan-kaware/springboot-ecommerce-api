package com.jeevan.ecommerce.controller;

import com.jeevan.ecommerce.dto.response.OrderResponse;
import com.jeevan.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse placeOrder(
            Authentication authentication
    ) {

        return orderService.placeOrder(
                authentication.getName()
        );
    }


    @GetMapping
    public List<OrderResponse> myOrders(
            Authentication authentication
    ) {

        return orderService.getMyOrders(
                authentication.getName()
        );
    }


    @GetMapping("/{orderId}")
    public OrderResponse getOrder(
            @PathVariable Long orderId
    ) {

        return orderService.getOrderById(orderId);
    }

}