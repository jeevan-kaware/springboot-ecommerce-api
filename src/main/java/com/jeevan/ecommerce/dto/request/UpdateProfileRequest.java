package com.jeevan.ecommerce.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    @NotBlank(message="Full name is required")
    private String fullName;

}