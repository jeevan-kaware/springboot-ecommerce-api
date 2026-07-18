package com.jeevan.ecommerce.controller.admin;

import com.jeevan.ecommerce.dto.request.ProductRequest;
import com.jeevan.ecommerce.dto.response.ProductResponse;
import com.jeevan.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;


    @PostMapping
    public ProductResponse createProduct(
            @Valid @RequestBody ProductRequest request) {

        return productService.createProduct(request);
    }


    @PutMapping("/{id}")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return productService.updateProduct(id, request);
    }
    @DeleteMapping("/{id}")
    public String deleteProduct(
            @PathVariable Long id) {

        return productService.deleteProduct(id);
    }

    @PostMapping(
            value = "/{id}/upload-image",
            consumes = "multipart/form-data"
    )
    public ProductResponse uploadImage(

            @PathVariable Long id,

            @Parameter(
                    description = "Product Image",
                    schema = @Schema(type = "string", format = "binary")
            )
            @RequestPart("file")
            MultipartFile file
    ) {

        return productService.uploadProductImage(id, file);
    }


}
