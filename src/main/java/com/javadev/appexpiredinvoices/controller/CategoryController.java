package com.javadev.appexpiredinvoices.controller;

import com.javadev.appexpiredinvoices.payload.CategoryDto;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.service.CategoryService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping(value = "/api/category")
public class CategoryController {

    final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping("/one")
    public HttpEntity<?> addCategory(@RequestBody CategoryDto categoryDto) {
        Response response = categoryService.addCategory(categoryDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping
    public HttpEntity<?> addCategories(@RequestBody List<CategoryDto> categoryDto) {
        Response response = categoryService.addAllCategory(categoryDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public HttpEntity<?> getCategory(@PathVariable Long id) {
        Response response = categoryService.getCategory(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN','USER')")
    @GetMapping
    public HttpEntity<?> getAllCategory() {
        Response response = categoryService.getAllCategory();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PutMapping("/{id}")
    public HttpEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        Response response = categoryService.updateCategory(id,categoryDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCategory(@PathVariable Long id) {
        Response response = categoryService.deleteCategory(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping
    public HttpEntity<?> deleteAllCategory() {
        Response response = categoryService.deleteAllCategory();
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
}
