package com.jeevan.ecommerce.controller.user;

import com.jeevan.ecommerce.dto.response.CategoryResponse;
import com.jeevan.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public List<CategoryResponse> getAllCategories() {

        return categoryService.getAllCategories();
    }


    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(
            @PathVariable Long id) {

        return categoryService.getCategoryById(id);
    }

}