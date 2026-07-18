package com.jeevan.ecommerce.controller;

import com.jeevan.ecommerce.dto.request.CategoryRequest;
import com.jeevan.ecommerce.dto.response.CategoryResponse;
import com.jeevan.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public List<CategoryResponse> getAllCategories() {

        return categoryService.getAllCategories();
    }


    @GetMapping("/categories/{id}")
    public CategoryResponse getCategoryById(
            @PathVariable Long id) {

        return categoryService.getCategoryById(id);
    }

}