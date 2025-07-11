package com.paulofranklins.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "product")
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
}
