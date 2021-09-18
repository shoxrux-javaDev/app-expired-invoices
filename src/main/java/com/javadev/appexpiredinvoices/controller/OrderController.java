package com.javadev.appexpiredinvoices.controller;

import com.javadev.appexpiredinvoices.payload.OrdersDto;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.service.OrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @PostMapping("/one")
    public HttpEntity<?> addOrder(@RequestBody OrdersDto ordersDto) {
        Response response = orderService.addOrder(ordersDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping
    public HttpEntity<?> addOrders(@RequestBody List<OrdersDto> ordersDto) {
        Response response = orderService.addOrders(ordersDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public HttpEntity<?> getOrder(@PathVariable UUID id) {
        Response response = orderService.getOrder(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }


    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping
    public HttpEntity<?> getOrders() {
        Response response = orderService.getOrders();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteOrder(@PathVariable UUID id) {
        Response response = orderService.deleteOrder(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping
    public HttpEntity<?> deleteOrders() {
        Response response = orderService.deleteOrders();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/orders_without_details")
    public HttpEntity<?> getOrdersWithoutDetails() {
        Response response = orderService.getOrdersWithoutDetails();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/customers_without_orders")
    public HttpEntity<?> getCustomerWithoutOrders() {
        Response response = orderService.getCustomerWithoutOrders();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @GetMapping("/customers_last_orders")
    public HttpEntity<?> getCustomerLastOrder(@RequestParam UUID id, @RequestParam String date) {
        Response response = orderService.getCustomerLastOrder(id, date);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/number_of_products_in_year")
    public HttpEntity<?> getAllOrderInYear() {
        Response response = orderService.getAllOrderInYear();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/orders_without_invoices")
    public HttpEntity<?> getOrderWithoutInvoice() {
        Response response = orderService.getOrderWithoutInvoice();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }




}
