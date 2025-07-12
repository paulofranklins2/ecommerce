package com.paulofranklins.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "product")
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private @NotBlank String name;
    private @NotBlank String description;
    private @NotBlank BigDecimal price;
    private @NotBlank String imageUrl;
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private @NotBlank Category category;

    public Product(@NotBlank String name, @NotBlank String description, @NotBlank BigDecimal price, @NotBlank String imageUrl, @NotBlank Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }
}
