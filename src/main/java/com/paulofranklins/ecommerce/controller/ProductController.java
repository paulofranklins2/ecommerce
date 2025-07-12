package com.paulofranklins.ecommerce.controller;

import com.paulofranklins.ecommerce.dto.ProductDto;
import com.paulofranklins.ecommerce.model.Category;
import com.paulofranklins.ecommerce.model.Product;
import com.paulofranklins.ecommerce.service.CategoryService;
import com.paulofranklins.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto productDto) {
        if (productExists(productDto.getName())) return status(CONFLICT).build();

        Optional<Category> category = existCategoryById(productDto);
        if (category.isEmpty()) return status(NOT_FOUND).build();

        productService.create(productDto, category.get());
        return status(CREATED).body(productDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        List<Product> products = productService.getAll();

        if (products.isEmpty()) return noContent().build();

        List<ProductDto> dtoList = products.stream().map(this::toDto).collect(Collectors.toList());

        return ok(dtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Integer id, @Valid @RequestBody ProductDto productDto) {
        if (!productExistsById(id)) return notFound().build();

        Product updated = productService.update(id, productDto);
        return ok(toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!productExistsById(id)) return notFound().build();

        productService.deleteById(id);
        return ok().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDto> findByName(@PathVariable String name) {
        Product product = productService.findByName(name);
        if (product == null) return notFound().build();

        return ok(toDto(product));
    }

    private ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setCategoryId(product.getCategory().getId());
        return dto;
    }

    private boolean productExists(String name) {
        return productService.findByName(name) != null;
    }

    private boolean productExistsById(Integer id) {
        return productService.findById(id) != null;
    }

    private Optional<Category> existCategoryById(ProductDto productDto) {
        return Optional.ofNullable(categoryService.findById(productDto.getCategoryId()));
    }

}
