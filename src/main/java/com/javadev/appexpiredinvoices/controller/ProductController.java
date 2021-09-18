package com.javadev.appexpiredinvoices.controller;

import com.javadev.appexpiredinvoices.payload.ProductDto;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.service.ProductService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping("/one")
    public HttpEntity<?> addProduct(@RequestBody ProductDto productDto) {
        Response response = productService.addProduct(productDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping
    public HttpEntity<?> addProducts(@RequestBody List<ProductDto> productDtoList) {
        Response response = productService.addProducts(productDtoList);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public HttpEntity<?> getProduct(@PathVariable UUID id) {
        Response response = productService.getProduct(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @GetMapping
    public HttpEntity<?> getProducts() {
        Response response = productService.getProducts();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PutMapping("/{id}")
    public HttpEntity<?> editProduct(@PathVariable UUID id, @RequestBody ProductDto productDto) {
        Response response = productService.editProduct(id, productDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable UUID id) {
        Response response = productService.deleteProduct(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping
    public HttpEntity<?> deleteProducts() {
        Response response = productService.deleteProducts();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
