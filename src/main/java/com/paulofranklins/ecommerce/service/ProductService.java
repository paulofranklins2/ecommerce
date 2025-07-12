package com.paulofranklins.ecommerce.service;

import com.paulofranklins.ecommerce.dto.ProductDto;
import com.paulofranklins.ecommerce.model.Category;
import com.paulofranklins.ecommerce.model.Product;
import com.paulofranklins.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public Product create(@Valid @NotBlank ProductDto productDto, Category category) {
        return productRepository.save(
                new Product(
                        productDto.getName(),
                        productDto.getDescription(),
                        productDto.getPrice(),
                        productDto.getImageUrl(),
                        category
                ));
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product findByName(@NotBlank String productName) {
        return productRepository.findByName(productName);
    }

    public Product findById(@NotBlank Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product update(@NotBlank Integer id, @Valid ProductDto productDto) {
        Product product = findById(id);
        updateProductMap(productDto, product);
        return productRepository.save(product);
    }

    private void updateProductMap(ProductDto productDto, Product product) {
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setCategory(categoryService.findById(productDto.getCategoryId()));
    }

    public void deleteById(@NotBlank Integer id) {
        productRepository.deleteById(id);
    }
}
