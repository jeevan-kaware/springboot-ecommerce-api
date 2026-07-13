package com.jeevan.ecommerce.service.impl;

import com.jeevan.ecommerce.dto.request.CategoryRequest;
import com.jeevan.ecommerce.dto.response.CategoryResponse;
import com.jeevan.ecommerce.entity.Category;
import com.jeevan.ecommerce.repository.CategoryRepository;
import com.jeevan.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.jeevan.ecommerce.exception.DuplicateResourceException;
import com.jeevan.ecommerce.exception.ResourceNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {

        if (repository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Category already exists");
        }

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Category savedCategory = repository.save(category);

        return CategoryResponse.builder()
                .id(savedCategory.getId())
                .name(savedCategory.getName())
                .description(savedCategory.getDescription())
                .build();
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return repository.findAll()
                .stream()
                .map(category -> CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .description(category.getDescription())
                        .build())
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {

        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {

        Category category = repository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Category not found"));;

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category updatedCategory = repository.save(category);

        return CategoryResponse.builder()
                .id(updatedCategory.getId())
                .name(updatedCategory.getName())
                .description(updatedCategory.getDescription())
                .build();
    }

    @Override
    public String deleteCategory(Long id) {

        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        repository.delete(category);

        return "Category deleted successfully";
    }
}