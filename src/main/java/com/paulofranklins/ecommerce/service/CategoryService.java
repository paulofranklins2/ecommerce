package com.paulofranklins.ecommerce.service;

import com.paulofranklins.ecommerce.model.Category;
import com.paulofranklins.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }

    public Category findByCategoryName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }

    public Category updateCategory(Integer categoryId, Category newCategory) {
        Category category = categoryRepository.findById(categoryId).get();
        category.setName(newCategory.getName());
        category.setDescription(newCategory.getDescription());
        category.setImageUrl(newCategory.getImageUrl());
        return categoryRepository.save(category);
    }
}
