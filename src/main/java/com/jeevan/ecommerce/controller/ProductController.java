package com.jeevan.ecommerce.controller;

import com.jeevan.ecommerce.dto.request.ProductRequest;
import com.jeevan.ecommerce.dto.response.ProductResponse;
import com.jeevan.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping
    public Page<ProductResponse> getProducts(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy) {

        return productService.getProducts(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(
            @PathVariable Long id) {

        return productService.getProductById(id);
    }




    @GetMapping("/search")
    public List<ProductResponse> searchProducts(
            @RequestParam String keyword) {

        return productService.searchProducts(keyword);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductResponse> getProductsByCategory(
            @PathVariable Long categoryId) {

        return productService.getProductsByCategory(categoryId);
    }

}