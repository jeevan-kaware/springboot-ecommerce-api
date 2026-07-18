package com.jeevan.ecommerce.service.impl;

import com.jeevan.ecommerce.dto.request.AddToCartRequest;
import com.jeevan.ecommerce.dto.response.CartItemResponse;
import com.jeevan.ecommerce.dto.response.CartResponse;
import com.jeevan.ecommerce.entity.Cart;
import com.jeevan.ecommerce.entity.CartItem;
import com.jeevan.ecommerce.entity.Product;
import com.jeevan.ecommerce.entity.User;
import com.jeevan.ecommerce.exception.ResourceNotFoundException;
import com.jeevan.ecommerce.repository.CartItemRepository;
import com.jeevan.ecommerce.repository.CartRepository;
import com.jeevan.ecommerce.repository.ProductRepository;
import com.jeevan.ecommerce.repository.UserRepository;
import com.jeevan.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public CartResponse addToCart(
            String email,
            AddToCartRequest request) {


        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));


        Product product = productRepository.findById(
                        request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"));



        if(product.getStock() < request.getQuantity()){

            throw new IllegalArgumentException("Insufficient stock");

        }



        Cart cart = cartRepository.findByUserId(
                user.getId()
        ).orElseGet(() -> {

            Cart newCart = Cart.builder()
                    .user(user)
                    .build();

            return cartRepository.save(newCart);

        });



        CartItem cartItem =
                cartItemRepository
                        .findByCartIdAndProductId(
                                cart.getId(),
                                product.getId()
                        )
                        .orElse(null);



        if (cartItem != null) {

            int newQuantity =
                    cartItem.getQuantity()
                            + request.getQuantity();

            if (newQuantity > product.getStock()) {
                throw new IllegalArgumentException("Insufficient stock");
            }

            cartItem.setQuantity(newQuantity);



        }else{


            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();

        }



        cartItemRepository.save(cartItem);

        if(!cart.getCartItems().contains(cartItem)){
            cart.getCartItems().add(cartItem);
        }

        return buildCartResponse(cart);

    }
    private CartResponse buildCartResponse(Cart cart) {

        List<CartItemResponse> items = new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cart.getCartItems()) {

            BigDecimal subTotal =
                    item.getProduct()
                            .getPrice()
                            .multiply(
                                    BigDecimal.valueOf(item.getQuantity())
                            );

            total = total.add(subTotal);

            items.add(

                    CartItemResponse.builder()
                            .cartItemId(item.getId())
                            .productId(item.getProduct().getId())
                            .productName(item.getProduct().getName())
                            .quantity(item.getQuantity())
                            .price(item.getProduct().getPrice())
                            .subTotal(subTotal)
                            .build()

            );
        }

        return CartResponse.builder()
                .cartId(cart.getId())
                .totalPrice(total)
                .items(items)
                .build();
    }

    @Override
    public CartResponse getCart(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        return buildCartResponse(cart);
    }

    @Override
    public String removeItem( String email,Long cartItemId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart Item not found"));
        if(!item.getCart().getId().equals(cart.getId())){
            throw new RuntimeException(
                    "You cannot remove another user's cart item");
        }

        cart.getCartItems().remove(item);
        cartRepository.save(cart);
        cartItemRepository.delete(item);

        return "Item removed successfully";
    }
    @Override
    public CartResponse updateQuantity( String email, Long cartItemId, Integer quantity) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart item not found"
                        ));

        if(!item.getCart().getId().equals(cart.getId())){
            throw new RuntimeException(
                    "You cannot update another user's cart");
        }
        if(quantity <= 0){
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }


        Product product = item.getProduct();

        if(quantity > product.getStock()){
            throw new IllegalArgumentException("Insufficient stock");
        }

        item.setQuantity(quantity);


        cartItemRepository.save(item);


        return buildCartResponse(item.getCart());
    }
    @Override
    public String clearCart(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        cartItemRepository.deleteAll(cart.getCartItems());

        cart.getCartItems().clear();

        cartRepository.save(cart);

        return "Cart cleared successfully";
    }
}