package com.javadev.appexpiredinvoices.controller;

import com.javadev.appexpiredinvoices.payload.CustomerRegDto;
import com.javadev.appexpiredinvoices.payload.LoginDto;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.service.CustomerRegService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class CustomerRegController {
    final CustomerRegService customerRegService;


    public CustomerRegController(CustomerRegService customerRegService) {
        this.customerRegService = customerRegService;
    }

    @PostMapping("/register")
    public HttpEntity<?> postCustomersInfo(@Valid @RequestBody CustomerRegDto customerRegDto) {
        Response response = customerRegService.postCustomerInfo(customerRegDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/verifyEmail")
    public HttpEntity<?> verify(@RequestParam String email, @RequestParam String emailCode) {
        Response response = customerRegService.verifyEmail(email, emailCode);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PostMapping("/login")
    public HttpEntity<?> customerLogin(@RequestBody LoginDto loginDto) {
        Response response = customerRegService.loginToSystem(loginDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

}
