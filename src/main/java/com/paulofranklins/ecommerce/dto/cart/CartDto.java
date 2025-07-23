package com.paulofranklins.ecommerce.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CartDto {
    private List<CartItemDto> cartItems;
    private BigDecimal total;
}
