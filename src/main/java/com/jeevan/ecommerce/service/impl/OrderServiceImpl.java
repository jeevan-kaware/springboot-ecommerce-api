package com.jeevan.ecommerce.service.impl;

import com.jeevan.ecommerce.dto.response.OrderItemResponse;
import com.jeevan.ecommerce.dto.response.OrderResponse;
import com.jeevan.ecommerce.entity.*;
import com.jeevan.ecommerce.enums.OrderStatus;
import com.jeevan.ecommerce.exception.ResourceNotFoundException;
import com.jeevan.ecommerce.repository.*;
import com.jeevan.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public OrderResponse placeOrder(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        order = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getCartItems()) {

            Product product = cartItem.getProduct();

            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException(
                        product.getName() + " is out of stock"
                );
            }

            product.setStock(
                    product.getStock() - cartItem.getQuantity()
            );

            productRepository.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .price(product.getPrice())
                    .build();

            orderItems.add(orderItem);

            total = total.add(
                    product.getPrice().multiply(
                            BigDecimal.valueOf(cartItem.getQuantity())
                    )
            );
        }
        orderItemRepository.saveAll(orderItems);

        order.setOrderItems(orderItems);

        order.setTotalAmount(total);

        orderRepository.save(order);

        cart.getCartItems().clear();

        cartRepository.save(cart);

        return mapToResponse(order);

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
    @Override
    public List<OrderResponse> getMyOrders(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();

    }
    @Override
    public OrderResponse getOrderById(String email,Long orderId) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                      new ResourceNotFoundException("User not found"));
        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Order not found"));

        if(!order.getUser().getId().equals(user.getId())){
            throw new RuntimeException(
                    "You cannot view another user's order"
            );
        }

        return mapToResponse(order);

    }
}