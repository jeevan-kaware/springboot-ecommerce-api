package com.jeevan.ecommerce.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private String brand;

    private String imageUrl;

    private Long categoryId;

}