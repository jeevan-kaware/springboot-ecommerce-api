package com.jeevan.ecommerce.controller.user;


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




    @GetMapping
    public CartResponse getCart(
            Authentication authentication
    ){

        return cartService.getCart(
                authentication.getName()
        );
    }




    @PutMapping("/update/{cartItemId}")
    public CartResponse updateQuantity(

            Authentication authentication,

            @PathVariable Long cartItemId,

            @RequestParam Integer quantity
    ){

        return cartService.updateQuantity(

                authentication.getName(),

                cartItemId,

                quantity
        );
    }




    @DeleteMapping("/remove/{cartItemId}")
    public String removeItem(

            Authentication authentication,

            @PathVariable Long cartItemId
    ){

        return cartService.removeItem(

                authentication.getName(),

                cartItemId
        );
    }




    @DeleteMapping("/clear")
    public String clearCart(
            Authentication authentication
    ){

        return cartService.clearCart(
                authentication.getName()
        );
    }

}