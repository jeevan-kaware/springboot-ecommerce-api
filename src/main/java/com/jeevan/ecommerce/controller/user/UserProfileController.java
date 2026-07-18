package com.jeevan.ecommerce.controller.user;


import com.jeevan.ecommerce.dto.request.UpdateProfileRequest;
import com.jeevan.ecommerce.dto.response.UserProfileResponse;
import com.jeevan.ecommerce.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserProfileController {


    private final UserProfileService userProfileService;



    @GetMapping("/profile")
    public UserProfileResponse getProfile(
            Authentication authentication
    ){

        return userProfileService.getProfile(
                authentication.getName()
        );
    }



    @PutMapping("/profile")
    public UserProfileResponse updateProfile(

            Authentication authentication,

            @Valid @RequestBody UpdateProfileRequest request

    ){

        return userProfileService.updateProfile(

                authentication.getName(),

                request
        );
    }

}