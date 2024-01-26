package com.project.shopapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    // hiển thị tất cả các category
    @GetMapping("")  // http://localhost:8088/api/v1/categories
    public ResponseEntity<String> getAllCategorie() {
        return ResponseEntity.ok("Chao ban, haha");
    }

    @PostMapping("")
    public ResponseEntity<String> insertCategories() {
        return ResponseEntity.ok("This is insertCategories");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategories(@PathVariable Long id) {
        return ResponseEntity.ok("insertCategories with id " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategories(@PathVariable Long id) {
        return ResponseEntity.ok("delete categories with id " + id);
    }
}