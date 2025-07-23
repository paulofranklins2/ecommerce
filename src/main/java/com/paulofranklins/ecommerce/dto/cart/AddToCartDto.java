package com.paulofranklins.ecommerce.dto.cart;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddToCartDto {
    private Integer id;
    private @NotBlank Integer productId;
    private @NotBlank Integer quantity;
}
