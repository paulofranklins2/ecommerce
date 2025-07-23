package com.paulofranklins.ecommerce.service;

import com.paulofranklins.ecommerce.dto.cart.AddToCartDto;
import com.paulofranklins.ecommerce.dto.cart.CartDto;
import com.paulofranklins.ecommerce.dto.cart.CartItemDto;
import com.paulofranklins.ecommerce.model.Cart;
import com.paulofranklins.ecommerce.model.Product;
import com.paulofranklins.ecommerce.model.User;
import com.paulofranklins.ecommerce.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addToCart(AddToCartDto addToCartDto, Product product, User user) {
        cartRepository.save(new Cart(product, addToCartDto.getQuantity(), user));
    }

    public CartDto cartItems(User user) {
        List<Cart> catList = cartRepository.findAllByUserOrderByCreatedAt(user);

        List<CartItemDto> cartItemsList = new ArrayList<>();

        for (Cart cart : catList) {
            cartItemsList.add(new CartItemDto(cart));
        }

        BigDecimal total = BigDecimal.ZERO;
        for (CartItemDto cartItem : cartItemsList) {
            BigDecimal itemTotal = cartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            total = total.add(itemTotal);
        }

        return new CartDto(cartItemsList, total);
    }
}
