package com.jeevan.ecommerce.controller;


import com.jeevan.ecommerce.dto.request.AddToCartRequest;
import com.jeevan.ecommerce.dto.response.CartResponse;
import com.jeevan.ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;


    // Add product to cart
    @PostMapping("/add")
    public CartResponse addToCart(
            Authentication authentication,
            @Valid @RequestBody AddToCartRequest request
    ){

        return cartService.addToCart(
                authentication.getName(),
                request
        );
    }



    // Get logged in user cart
    @GetMapping
    public CartResponse getCart(
            Authentication authentication
    ){

        return cartService.getCart(
                authentication.getName()
        );
    }



    // Update quantity
    @PutMapping("/update/{cartItemId}")
    public CartResponse updateQuantity(
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity
    ){

        return cartService.updateQuantity(
                cartItemId,
                quantity
        );
    }



    // Remove single item
    @DeleteMapping("/remove/{cartItemId}")
    public String removeItem(
            @PathVariable Long cartItemId
    ){

        return cartService.removeItem(cartItemId);
    }



    // Clear complete cart
    @DeleteMapping("/clear")
    public String clearCart(
            Authentication authentication
    ){

        return cartService.clearCart(
                authentication.getName()
        );
    }

}