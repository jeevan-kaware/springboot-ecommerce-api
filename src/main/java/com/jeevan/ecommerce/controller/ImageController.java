package com.jeevan.ecommerce.controller;


import com.jeevan.ecommerce.dto.response.ProductResponse;

import com.jeevan.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/admin/images")
@RequiredArgsConstructor
public class ImageController {




    private final ProductService productService;


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