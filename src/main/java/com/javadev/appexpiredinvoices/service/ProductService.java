package com.javadev.appexpiredinvoices.service;

import com.javadev.appexpiredinvoices.entity.Attachment;
import com.javadev.appexpiredinvoices.entity.Category;
import com.javadev.appexpiredinvoices.entity.Product;
import com.javadev.appexpiredinvoices.payload.ProductDto;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.repo.AttachmentRepo;
import com.javadev.appexpiredinvoices.repo.CategoryRepo;
import com.javadev.appexpiredinvoices.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    final ProductRepo productRepo;
    final CategoryRepo categoryRepo;
    final AttachmentRepo attachmentRepo;

    public ProductService(ProductRepo productRepo, CategoryRepo categoryRepo,
                          AttachmentRepo attachmentRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.attachmentRepo = attachmentRepo;
    }

    public Response addProduct(ProductDto productDto) {
        Optional<Category> categoryOptional = categoryRepo.findById(productDto.getCategoryId());
        if (categoryOptional.isEmpty()) return new Response("category not found", false);
        Product product = new Product();
        product.setCategory(categoryOptional.get());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        Optional<Attachment> attachmentOptional = attachmentRepo.findById(productDto.getAttachmentId());
        attachmentOptional.ifPresent(product::setAttachment);
        productRepo.save(product);
        return new Response("add product", true);
    }

    public Response addProducts(List<ProductDto> productDtoList) {
        for (ProductDto productDto : productDtoList) {
            Optional<Category> categoryOptional = categoryRepo.findById(productDto.getCategoryId());
            if (categoryOptional.isEmpty()) return new Response("category not found", false);
            Product product = new Product();
            product.setCategory(categoryOptional.get());
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            Optional<Attachment> attachmentOptional = attachmentRepo.findById(productDto.getAttachmentId());
            attachmentOptional.ifPresent(product::setAttachment);
            productRepo.save(product);
        }
        return new Response("add product", true);
    }

    public Response getProduct(UUID id) {
        Optional<Product> productOptional = productRepo.findById(id);
        if (productOptional.isEmpty()) return new Response("product not found", false);
        return new Response("success", true, productOptional.get());
    }

    public Response getProducts() {
        List<Product> productList = productRepo.findAll();
        if (productList.isEmpty()) return new Response("products not found", false);
        return new Response("success", true, productList);
    }

    public Response editProduct(UUID id, ProductDto productDto) {
        Optional<Category> categoryOptional = categoryRepo.findById(productDto.getCategoryId());
        if (categoryOptional.isEmpty()) return new Response("category not found", false);
        Optional<Product> productOptional = productRepo.findById(id);
        if (productOptional.isEmpty()) return new Response("product not found", false);
        Product product = productOptional.get();
        product.setCategory(categoryOptional.get());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        Optional<Attachment> attachmentOptional = attachmentRepo.findById(productDto.getAttachmentId());
        attachmentOptional.ifPresent(product::setAttachment);
        productRepo.save(product);
        return new Response("edited product", true);
    }

    public Response deleteProduct(UUID id) {
        Optional<Product> productOptional = productRepo.findById(id);
        if (productOptional.isEmpty()) return new Response("product not found", false);
        productRepo.deleteById(id);
        return new Response("deleted", true);
    }

    public Response deleteProducts() {
        productRepo.deleteAll();
        return new Response("deleted all", true);
    }
}
