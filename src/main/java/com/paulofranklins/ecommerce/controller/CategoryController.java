package com.paulofranklins.ecommerce.controller;

import com.paulofranklins.ecommerce.model.Category;
import com.paulofranklins.ecommerce.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = categoryService.findAll();
        return categories.isEmpty() ? noContent().build() : ok(categories);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Category> getByName(@PathVariable String name) {
        boolean exists = existsByCategoryName(name);
        return exists ? ok(categoryService.findByCategoryName(name)) : notFound().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Category> getById(@PathVariable Integer id) {
        boolean exists = existsByCategoryId(id);
        return exists ? ok(categoryService.findById(id)) : notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean exists = existsByCategoryId(id);
        if (!exists) return notFound().build();

        categoryService.deleteById(id);
        return noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Integer id, @RequestBody Category category) {
        if (!existsByCategoryId(id)) return notFound().build();
        return status(ACCEPTED).body(categoryService.updateCategory(id, category));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        if (existsByCategoryName(category.getName())) return status(CONFLICT).body(category);
        return status(CREATED).body(categoryService.save(category));
    }

    private boolean existsByCategoryName(String categoryName) {
        return nonNull(categoryService.findByCategoryName(categoryName));
    }

    private boolean existsByCategoryId(Integer categoryId) {
        return nonNull(categoryService.findById(categoryId));
    }
}
