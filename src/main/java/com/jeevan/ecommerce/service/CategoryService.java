package com.jeevan.ecommerce.service;

import com.jeevan.ecommerce.dto.request.CategoryRequest;
import com.jeevan.ecommerce.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    String deleteCategory(Long id);

}