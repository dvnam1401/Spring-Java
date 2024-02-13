package com.project.shopapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dtos.CategoryDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    // hiển thị tất cả các category
    @GetMapping("") // http://localhost:8088/api/v1/categories?page=1&limit=10
    public ResponseEntity<String> getAllCategorie(
            @RequestParam("page") int categoriesPage,
            @RequestParam("limit") int categoriesLimit) {
        return ResponseEntity
                .ok(String.format("getAllCategories, page = %d, limit = %d", categoriesPage, categoriesLimit));
    }

    @PostMapping("")
    // nếu tham số truyền vào là 1 object thì sao? => Data Transfer Object (DTO) =
    // Request Object
    public ResponseEntity<?> insertCategories(@Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result) {
        if (result.hasErrors()) {
            // result.getFieldErrors().stream().map(fieldError ->
            // fieldError.getDefaultMessage()).toList();
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages.toString());
        }
        return ResponseEntity.ok("This is insertCategories " + categoryDTO);
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