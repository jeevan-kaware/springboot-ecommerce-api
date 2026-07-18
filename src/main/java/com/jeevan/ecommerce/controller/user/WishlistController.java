package com.jeevan.ecommerce.controller.user;


import com.jeevan.ecommerce.dto.response.WishlistResponse;
import com.jeevan.ecommerce.service.WishlistService;

import lombok.RequiredArgsConstructor;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/api/user/wishlist")
@RequiredArgsConstructor
public class WishlistController {



    private final WishlistService wishlistService;



    @PostMapping("/{productId}")
    public WishlistResponse addWishlist(

            Authentication authentication,

            @PathVariable Long productId

    ){

        return wishlistService.addToWishlist(
                authentication.getName(),
                productId
        );

    }




    @GetMapping
    public List<WishlistResponse> getWishlist(

            Authentication authentication

    ){

        return wishlistService.getWishlist(
                authentication.getName()
        );

    }





    @DeleteMapping("/{productId}")
    public String removeWishlist(

            Authentication authentication,

            @PathVariable Long productId

    ){

        return wishlistService.removeFromWishlist(
                authentication.getName(),
                productId
        );

    }

}