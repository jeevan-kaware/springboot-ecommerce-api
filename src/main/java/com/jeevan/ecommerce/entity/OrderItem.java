package com.jeevan.ecommerce.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private Integer quantity;


    private BigDecimal price;



    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;



    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

}