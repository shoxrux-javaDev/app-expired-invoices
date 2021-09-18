package com.javadev.appexpiredinvoices.service;

import com.javadev.appexpiredinvoices.entity.Customer;
import com.javadev.appexpiredinvoices.payload.CustomerEditDto;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.repo.CustomerRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerOwnService {

    final CustomerRepo customerRepo;

    public CustomerOwnService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Response editInfo(CustomerEditDto customerEditDto) {
        Customer customFmToken = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Customer> customerOptional = customerRepo.findByEmail(customFmToken.getEmail());
        if (customerOptional.isEmpty()) return new Response("customer not found", false);
        Customer customer = customerOptional.get();
        customer.setUsername(customerEditDto.getUsername());
        customer.setPassword(customerEditDto.getPassword());
        customer.setPhone(customerEditDto.getPhone());
        customer.setEmail(customerEditDto.getEmail());
        customer.setCountry(customerEditDto.getCountry());
        customer.setAddress(customerEditDto.getAddress());
        customerRepo.save(customer);
        return new Response("successfully edited", true);
    }

    public Response getInfo() {
        Customer customForGet = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Customer> customerOptional = customerRepo.findByEmail(customForGet.getEmail());
        if (customerOptional.isEmpty()) return new Response("customer info not found", false);
        Customer customer = customerOptional.get();
        return new Response("success", true, customer);
    }

    public Response getCustomerInfo(UUID id) {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        if (customerOptional.isEmpty()) return new Response("customer not found", false);
        return new Response("success", true, customerOptional.get());
    }

    public Response getAllCustomers() {
        List<Customer> customerList = customerRepo.findAll();
        if (customerList.isEmpty()) return new Response("customers not found", false);
        return new Response("successfully found all customer", true, customerList);
    }

    public Response deleteInfo() {
        Customer customForDte = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Customer> customerOptional = customerRepo.findByEmail(customForDte.getEmail());
        if (customerOptional.isEmpty()) return new Response("customer not found", true);
        customerRepo.deleteById(customForDte.getId());
        return new Response("successfully deleted", true);
    }

    public Response customersWithoutOrders() {
        return null;
    }

    public Response deleteCustomerInfo(UUID id) {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        if (customerOptional.isEmpty()) return new Response("customer not found", false);
        customerRepo.deleteById(id);
        return new Response("deleted", true);
    }

    public Response editCustomerInfo(UUID id, CustomerEditDto customerEditDto) {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        if (customerOptional.isEmpty()) return new Response("customer not found", false);
        Customer customer = customerOptional.get();
        customer.setUsername(customerEditDto.getUsername());
        customer.setPassword(customerEditDto.getPassword());
        customer.setPhone(customerEditDto.getPhone());
        customer.setEmail(customerEditDto.getEmail());
        customer.setCountry(customerEditDto.getCountry());
        customer.setAddress(customerEditDto.getAddress());
        customerRepo.save(customer);
        return new Response("successfully edited", true);
    }

    public Response deleteAllInfo() {
        customerRepo.deleteAll();
        return new Response("deleted all", true);
    }
}
