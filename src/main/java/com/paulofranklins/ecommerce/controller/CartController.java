package com.paulofranklins.ecommerce.controller;

import com.paulofranklins.ecommerce.dto.cart.AddToCartDto;
import com.paulofranklins.ecommerce.dto.cart.CartDto;
import com.paulofranklins.ecommerce.model.Product;
import com.paulofranklins.ecommerce.model.User;
import com.paulofranklins.ecommerce.service.CartService;
import com.paulofranklins.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cart")
@RestController
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Product product = productService.findById(addToCartDto.getProductId());
        cartService.addToCart(addToCartDto, product, user);

        return ResponseEntity.ok(new ApiResponse(true, "added to cart"));
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        CartDto cartDto = cartService.cartItems(user);
        return ResponseEntity.ok(cartDto);
    }
}
