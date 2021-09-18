package com.javadev.appexpiredinvoices.controller;

import com.javadev.appexpiredinvoices.payload.PaymentDto;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.service.PaymentService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @PostMapping
    public HttpEntity<?> addPayment(@RequestBody PaymentDto paymentDto) {
        Response response = paymentService.addPayment(paymentDto);
        return ResponseEntity.status(response.isSuccess()?200:409).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public HttpEntity<?> getPayment(@PathVariable UUID id) {
        Response response = paymentService.getPayment(id);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping
    public HttpEntity<?> getPayments() {
        Response response = paymentService.getPayments();
        return ResponseEntity.status(response.isSuccess()?200:409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deletePayment(@PathVariable UUID  id) {
        Response response = paymentService.deletePayment(id);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping
    public HttpEntity<?> deleteAllPayment() {
        Response response = paymentService.deleteALlPayment();
        return ResponseEntity.status(response.isSuccess()?200:409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/overpaid_invoices")
    public HttpEntity<?> getOverPaidInvoice() {
        Response response = paymentService.getOverPaidInvoice();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

}
