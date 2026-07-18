package com.jeevan.ecommerce.entity;


import com.jeevan.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private BigDecimal totalAmount;


    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    private LocalDateTime createdAt;



    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
    orphanRemoval = true
    )
    private List<OrderItem> orderItems;


}