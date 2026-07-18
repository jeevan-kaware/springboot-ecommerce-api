package com.jeevan.ecommerce.service;

import com.jeevan.ecommerce.dto.request.ProductRequest;
import com.jeevan.ecommerce.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {


    ProductResponse createProduct(ProductRequest request);





    ProductResponse getProductById(Long id);


    List<ProductResponse> getProductsByCategory(Long categoryId);


    ProductResponse updateProduct(Long id,
                                  ProductRequest request);


    String deleteProduct(Long id);

    List<ProductResponse> searchProducts(String keyword);

    ProductResponse uploadProductImage(
            Long productId,
            MultipartFile file
    );
    Page<ProductResponse> getProducts(int page,
                                      int size,
                                      String sortBy);

}