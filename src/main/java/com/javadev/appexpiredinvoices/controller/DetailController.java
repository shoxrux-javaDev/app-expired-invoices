package com.javadev.appexpiredinvoices.controller;

import com.javadev.appexpiredinvoices.payload.DetailDto;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.service.DetailService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/detail")
public class DetailController {

    final DetailService detailService;

    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @PostMapping("/one")
    public HttpEntity<?> addDetail(@RequestBody DetailDto detailDto) {
        Response response = detailService.addDetail(detailDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping
    public HttpEntity<?> addAllDetail(@RequestBody List<DetailDto> detailDto) {
        Response response = detailService.addAllDetail(detailDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public HttpEntity<?> getDetail(@PathVariable UUID id) {
        Response response = detailService.getDetail(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping
    public HttpEntity<?> getAllDetail() {
        Response response = detailService.getAllDetail();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteDetail(@PathVariable UUID id) {
        Response response = detailService.deleteDetail(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping
    public HttpEntity<?> deleteAllDetail() {
        Response response = detailService.deleteAllDetail();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/high_demand_products")
    public HttpEntity<?> getHighDemandProducts() {
        Response response = detailService.getHighDemandProducts();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/bulk_products")
    public HttpEntity<?> getBulkProductPrice() {
        Response response = detailService.getBulkProductPrice();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

}
