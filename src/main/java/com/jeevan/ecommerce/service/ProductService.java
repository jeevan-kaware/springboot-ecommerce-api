package com.jeevan.ecommerce.service;

import com.jeevan.ecommerce.dto.request.ProductRequest;
import com.jeevan.ecommerce.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {


    ProductResponse createProduct(ProductRequest request);


    List<ProductResponse> getAllProducts();


    ProductResponse getProductById(Long id);


    List<ProductResponse> getProductsByCategory(Long categoryId);


    List<ProductResponse> searchProduct(String keyword);


    ProductResponse updateProduct(Long id,
                                  ProductRequest request);


    String deleteProduct(Long id);

    List<ProductResponse> searchProducts(String keyword);


    Page<ProductResponse> getProducts(int page,
                                      int size,
                                      String sortBy);

}