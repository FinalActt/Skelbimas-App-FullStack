package com.skelbimas_app_backend.skelbimas_app_backend.controller;

import com.skelbimas_app_backend.skelbimas_app_backend.models.categories;
import com.skelbimas_app_backend.skelbimas_app_backend.service.categoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class categoriesController {

    @Autowired
    private categoriesService categoriesService;

    // Get all categories
    @GetMapping("/public/api/categories")
    public List<categories> getAllCategories() {
        return categoriesService.getAllCategories();
    }

    // Get a category by id
    @GetMapping("/public/api/categories/{id}")
    public ResponseEntity<categories> getCategoryById(@PathVariable Integer id) {
        categories category = categoriesService.getCategoryById(id);
        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new category
    @PostMapping("/admin/api/categories/add")
    public ResponseEntity<?> createCategory(@RequestBody categories category) {
        // Check if the category with the provided tag already exists
        categories existingCategory = categoriesService.getCategoryByTag(category.getTag());
        if (existingCategory != null) {
            // If category already exists, return a warning message as JSON response
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category with tag '" + category.getTag() + "' already exists!");
        } else {
            // If category does not exist, create it
            categories newCategory = categoriesService.createCategory(category);
            // Return the created category
            return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
        }
    }

    // Update an existing category
    @PutMapping("/admin/api/categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody categories category) {
        categories updatedCategory = categoriesService.updateCategory(id, category);
        if (updatedCategory != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a category
    @DeleteMapping("/admin/api/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        categoriesService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}