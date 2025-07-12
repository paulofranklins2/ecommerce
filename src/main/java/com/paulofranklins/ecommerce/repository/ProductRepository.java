package com.paulofranklins.ecommerce.repository;

import com.paulofranklins.ecommerce.model.Product;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;

@Table
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByName(@NotBlank String name);
}
