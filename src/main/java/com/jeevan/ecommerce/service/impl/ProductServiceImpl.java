package com.jeevan.ecommerce.service.impl;


import com.jeevan.ecommerce.dto.request.ProductRequest;
import com.jeevan.ecommerce.dto.response.ProductResponse;
import com.jeevan.ecommerce.entity.Category;
import com.jeevan.ecommerce.entity.Product;
import com.jeevan.ecommerce.repository.CategoryRepository;
import com.jeevan.ecommerce.repository.ProductRepository;
import com.jeevan.ecommerce.service.ProductService;
import com.jeevan.ecommerce.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;



import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;



    @Override
    public ProductResponse createProduct(ProductRequest request) {


        Category category =
                categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Category not found"));


        Product product = Product.builder()

                .name(request.getName())

                .description(request.getDescription())

                .price(request.getPrice())

                .stock(request.getStock())

                .brand(request.getBrand())

                .imageUrl(request.getImageUrl())

                .category(category)

                .active(true)

                .build();



        Product savedProduct =
                productRepository.save(product);



        return mapToResponse(savedProduct);
    }



    @Override
    public List<ProductResponse> getAllProducts() {


        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

    }



    @Override
    public ProductResponse getProductById(Long id) {


        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Category not found"));


        return mapToResponse(product);

    }




    @Override
    public List<ProductResponse> getProductsByCategory(
            Long categoryId) {


        return productRepository
                .findByCategoryId(categoryId)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }




    @Override
    public List<ProductResponse> searchProduct(
            String keyword) {


        return productRepository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }




    @Override
    public ProductResponse updateProduct(
            Long id,
            ProductRequest request) {


        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Product not found"));



        Category category =
                categoryRepository.findById(
                                request.getCategoryId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Product not found"));


        product.setName(request.getName());

        product.setDescription(
                request.getDescription());

        product.setPrice(
                request.getPrice());

        product.setStock(
                request.getStock());

        product.setBrand(
                request.getBrand());

        product.setImageUrl(
                request.getImageUrl());

        product.setCategory(category);



        Product updatedProduct =
                productRepository.save(product);



        return mapToResponse(updatedProduct);

    }




    @Override
    public String deleteProduct(Long id) {


        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Product not found"));


        productRepository.delete(product);


        return "Product deleted successfully";

    }





    private ProductResponse mapToResponse(
            Product product) {


        return ProductResponse.builder()

                .id(product.getId())

                .name(product.getName())

                .description(product.getDescription())

                .price(product.getPrice())

                .stock(product.getStock())

                .brand(product.getBrand())

                .imageUrl(product.getImageUrl())

                .active(product.getActive())

                .categoryName(
                        product.getCategory()
                                .getName()
                )

                .build();

    }
    @Override
    public List<ProductResponse> searchProducts(String keyword) {

        return productRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    @Override
    public Page<ProductResponse> getProducts(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy)
                );

        return productRepository.findAll(pageable)
                .map(this::mapToResponse);

    }
}