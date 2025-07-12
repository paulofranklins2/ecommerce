package com.paulofranklins.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private @NotBlank Integer id;
    private @NotBlank String name;
    private @NotBlank String description;
    private @NotBlank String imageUrl;
}
