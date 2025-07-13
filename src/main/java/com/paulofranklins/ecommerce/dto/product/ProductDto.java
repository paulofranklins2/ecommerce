package com.paulofranklins.ecommerce.dto.product;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class ProductDto {
    private Integer id;
    private @NotBlank String name;
    private @NotBlank String description;
    private @NotBlank BigDecimal price;
    private @NotBlank String imageUrl;
    private @NotBlank Integer categoryId;
}
