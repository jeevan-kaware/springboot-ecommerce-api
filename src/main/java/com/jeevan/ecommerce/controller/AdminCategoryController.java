package com.jeevan.ecommerce.controller;

import com.jeevan.ecommerce.dto.request.CategoryRequest;
import com.jeevan.ecommerce.dto.response.CategoryResponse;
import com.jeevan.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping()
    public CategoryResponse createCategory(
            @Valid @RequestBody CategoryRequest request) {

        return categoryService.createCategory(request);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {

        return categoryService.updateCategory(id, request);
    }

    @DeleteMapping("{id}")
    public String deleteCategory(
            @PathVariable Long id) {

        return categoryService.deleteCategory(id);
    }
}
