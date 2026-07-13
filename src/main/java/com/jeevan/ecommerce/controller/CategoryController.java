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


    // ADMIN
    @PostMapping("/admin/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse createCategory(
            @Valid @RequestBody CategoryRequest request) {

        return categoryService.createCategory(request);
    }


    // USER + ADMIN
    @GetMapping("/categories")
    public List<CategoryResponse> getAllCategories() {

        return categoryService.getAllCategories();
    }


    // USER + ADMIN
    @GetMapping("/categories/{id}")
    public CategoryResponse getCategoryById(
            @PathVariable Long id) {

        return categoryService.getCategoryById(id);
    }


    // ADMIN
    @PutMapping("/admin/categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {

        return categoryService.updateCategory(id, request);
    }


    // ADMIN
    @DeleteMapping("/admin/categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCategory(
            @PathVariable Long id) {

        return categoryService.deleteCategory(id);
    }

}