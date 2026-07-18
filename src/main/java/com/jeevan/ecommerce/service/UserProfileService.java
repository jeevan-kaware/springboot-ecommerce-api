package com.jeevan.ecommerce.service;

import com.jeevan.ecommerce.dto.request.ChangePasswordRequest;
import com.jeevan.ecommerce.dto.request.UpdateProfileRequest;
import com.jeevan.ecommerce.dto.response.UserProfileResponse;

public interface UserProfileService {

    UserProfileResponse getProfile(String email);

    UserProfileResponse updateProfile(
            String email,
            UpdateProfileRequest request
    );

}