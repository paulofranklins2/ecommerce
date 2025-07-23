package com.paulofranklins.ecommerce.dto.cart;

import com.paulofranklins.ecommerce.model.Cart;
import com.paulofranklins.ecommerce.model.Product;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CartItemDto {
    private Integer id;
    private @NotBlank Integer quantity;
    private @NotBlank Product product;

    public CartItemDto(Cart cart) {
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.product = cart.getProduct();
    }
}
