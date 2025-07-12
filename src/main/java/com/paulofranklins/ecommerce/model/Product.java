package com.paulofranklins.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "product")
@RequiredArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private @NonNull @NotBlank String name;
    private @NonNull @NotBlank String description;
    private @NonNull @NotBlank BigDecimal price;
    private @NonNull @NotBlank String imageUrl;
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private @NonNull @NotBlank Category category;

}
