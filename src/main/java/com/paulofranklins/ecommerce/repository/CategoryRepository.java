package com.paulofranklins.ecommerce.repository;

import com.paulofranklins.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(@NotBlank String categoryName);
}
