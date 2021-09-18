package com.javadev.appexpiredinvoices.service;

import com.javadev.appexpiredinvoices.entity.Category;
import com.javadev.appexpiredinvoices.payload.CategoryDto;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.repo.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public Response addCategory(CategoryDto categoryDto) {
        Optional<Category> optionalCategory = categoryRepo.findByName(categoryDto.getName());
        if (optionalCategory.isEmpty()) return new Response("this category already saved", false);
        Category category = new Category();
        category.setName(categoryDto.getName());
        categoryRepo.save(category);
        return new Response("successfully saved", true);
    }

    public Response addAllCategory(List<CategoryDto> categoryDtoList) {
        for (CategoryDto categoryDto : categoryDtoList) {
            Optional<Category> categoryOptional = categoryRepo.findByName(categoryDto.getName());
            if (categoryOptional.isEmpty()) return new Response("this category already saved", false);
            Category category = new Category();
            category.setName(categoryDto.getName());
            categoryRepo.save(category);
            return new Response("successfully saved", true);
        }
        return new Response("don't added", false);
    }

    public Response updateCategory(Long id, CategoryDto categoryDto) {
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        if (categoryOptional.isEmpty()) return new Response("category not found", false);
        Category category = categoryOptional.get();
        category.setName(categoryDto.getName());
        categoryRepo.save(category);
        return new Response("successfully edited", true);
    }

    public Response deleteCategory(Long id) {
        categoryRepo.deleteById(id);
        return new Response("category deleted", true);
    }

    public Response getCategory(Long id) {
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        if (categoryOptional.isEmpty()) return new Response("category not found", false);
        return new Response("category is sending", true, categoryOptional.get());
    }

    public Response getAllCategory() {
        List<Category> categoryList = categoryRepo.findAll();
        if (categoryList.isEmpty()) return new Response("category list of empty", false);
        return new Response("category lists are sending", true, categoryList);
    }

    public Response deleteAllCategory() {
        categoryRepo.deleteAll();
        return new Response("deleted all", true);
    }
}
