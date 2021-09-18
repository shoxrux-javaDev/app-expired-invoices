package com.javadev.appexpiredinvoices.controller;

import com.javadev.appexpiredinvoices.payload.InvoiceDto;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.service.InvoiceService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @PostMapping("/one")
    public HttpEntity<?> addInvoice(@RequestBody InvoiceDto invoiceDto) {
        Response response = invoiceService.addInvoice(invoiceDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping
    public HttpEntity<?> addAllInvoice(@RequestBody List<InvoiceDto> invoiceDtoList) {
        Response response = invoiceService.addAllInvoice(invoiceDtoList);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public HttpEntity<?> getInvoice(@PathVariable UUID id) {
        Response response = invoiceService.getInvoice(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }


    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping
    public HttpEntity<?> getAllInvoice() {
        Response response = invoiceService.getAllInvoice();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteOneInvoice(@PathVariable UUID id) {
        Response response = invoiceService.deleteOneInvoice(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping
    public HttpEntity<?> deleteAllInvoices() {
        Response response = invoiceService.deleteAllInvoices();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/expired_invoices")
    public HttpEntity<?> getExpiredIv() {
        Response response = invoiceService.getExpiredIv();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/wrong_date_invoices")
    public HttpEntity<?> getWrongInvoice() {
        Response response = invoiceService.getWrongInvoice();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

}
