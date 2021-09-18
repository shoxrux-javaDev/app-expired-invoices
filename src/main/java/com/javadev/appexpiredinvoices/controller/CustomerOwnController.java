package com.javadev.appexpiredinvoices.controller;

import com.javadev.appexpiredinvoices.payload.CustomerEditDto;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.service.CustomerOwnService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/customer")
public class CustomerOwnController {

    final CustomerOwnService customerOwnService;

    public CustomerOwnController(CustomerOwnService customerOwnService) {
        this.customerOwnService = customerOwnService;
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @PutMapping
    public HttpEntity<?> editInfo(@RequestBody CustomerEditDto customerEditDto) {
        Response response = customerOwnService.editInfo(customerEditDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyAuthority('EDIT_CUSTOMER')")
    @PutMapping("/forAdmin/{id}")
    public HttpEntity<?> editCustomerInfo(@PathVariable UUID id,@RequestBody CustomerEditDto customerEditDto) {
        Response response = customerOwnService.editCustomerInfo(id,customerEditDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @GetMapping
    public HttpEntity<?> getInfo() {
        Response response = customerOwnService.getInfo();
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public HttpEntity<?> getCustomerInfo(@PathVariable UUID id) {
        Response response = customerOwnService.getCustomerInfo(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @GetMapping("/list")
    public HttpEntity<?> getAllCustomers() {
        Response response = customerOwnService.getAllCustomers();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @DeleteMapping
    public HttpEntity<?> deleteMyInfo() {
        Response response = customerOwnService.deleteInfo();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_CUSTOMER')")
    @DeleteMapping("/fa/{id}")
    public HttpEntity<?> deleteCustomerInfo(@PathVariable UUID id) {
        Response response = customerOwnService.deleteCustomerInfo(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_CUSTOMERS')")
    @DeleteMapping("/all")
    public HttpEntity<?> deleteAllInfo() {
        Response response = customerOwnService.deleteAllInfo();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
