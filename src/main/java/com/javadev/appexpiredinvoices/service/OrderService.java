package com.javadev.appexpiredinvoices.service;

import com.javadev.appexpiredinvoices.entity.Customer;
import com.javadev.appexpiredinvoices.entity.Orders;
import com.javadev.appexpiredinvoices.repo.projection.CustomerLastOrderProjection;
import com.javadev.appexpiredinvoices.repo.projection.GetCustomerWithoutOrders;
import com.javadev.appexpiredinvoices.repo.projection.OrderInterface;
import com.javadev.appexpiredinvoices.payload.OrdersDto;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.repo.CustomerRepo;
import com.javadev.appexpiredinvoices.repo.OrderRepo;
import com.javadev.appexpiredinvoices.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    final OrderRepo orderRepo;
    final CustomerRepo customerRepo;
    final ProductRepo productRepo;

    public OrderService(OrderRepo orderRepo, CustomerRepo customerRepo,
                        ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
    }

    public Response addOrder(OrdersDto ordersDto) {
        Optional<Customer> customerOptional = customerRepo.findById(ordersDto.getCustomerId());
        if (customerOptional.isEmpty()) return new Response("customer not found", false);
        Orders orders = new Orders();
        orders.setCustomer(customerOptional.get());
        orderRepo.save(orders);
        return new Response("successfully added", true);
    }

    public Response addOrders(List<OrdersDto> ordersDtoList) {
        for (OrdersDto ordersDto : ordersDtoList) {
            Optional<Customer> customerOptional = customerRepo.findById(ordersDto.getCustomerId());
            if (customerOptional.isEmpty()) return new Response("customer not found", false);
            Orders orders = new Orders();
            orders.setCustomer(customerOptional.get());
            orderRepo.save(orders);
        }
        return new Response("successfully added", true);
    }

    public Response getOrder(UUID id) {
        Optional<Orders> ordersOptional = orderRepo.findById(id);
        if (ordersOptional.isEmpty()) return new Response("order is not found", false);
        return new Response("success", true, ordersOptional.get());
    }

    public Response getOrders() {
        List<Orders> ordersList = orderRepo.findAll();
        if (ordersList.isEmpty()) return new Response("orderList is empty", false);
        return new Response("success", true, ordersList);
    }

    public Response deleteOrder(UUID id) {
        Optional<Orders> ordersOptional = orderRepo.findById(id);
        if (ordersOptional.isEmpty()) return new Response("order not found", false);
        orderRepo.deleteById(id);
        return new Response("deleted", true);
    }

    public Response deleteOrders() {
        orderRepo.deleteAll();
        return new Response("deleted all", true);
    }

    public Response getOrdersWithoutDetails() {
        List<Orders> orderList = orderRepo.ordersWithoutDetails();
        if (orderList.isEmpty()) return new Response("orderList is empty", false);
        return new Response("success", true, orderList);
    }

    public Response getCustomerWithoutOrders() {
        List<GetCustomerWithoutOrders> ordersList = orderRepo.customersWithoutOrders();
        if (ordersList.isEmpty()) return new Response("orderList is empty", false);
        return new Response("success", true, ordersList);
    }

    public Response getCustomerLastOrder() {
        List<CustomerLastOrderProjection> customerLastOrders = orderRepo.customerLastOrders();
        if (customerLastOrders.isEmpty()) return new Response("customer is not found", false);
        List<Object> lastOrder = new ArrayList<>();
        for (CustomerLastOrderProjection customerLastOrder : customerLastOrders) {
            lastOrder.add(customerLastOrder.getId());
            lastOrder.add(customerLastOrder.getName());
            lastOrder.add(customerLastOrder.getDate());
        }
        return new Response("success", true, lastOrder);
    }

    public Response getAllOrderInYear() {
        Integer integer = orderRepo.numberOfProductInYear();
        return new Response("success", true, integer);
    }

    public Response getOrderWithoutInvoice() {
        List<OrderInterface> ordersList = orderRepo.ordersWithoutInvoices();
        if (ordersList.isEmpty()) return new Response("orderList is empty", false);
        List<Object> idDateAndPrice = new ArrayList<>();
        ordersList.forEach(orders -> {
            idDateAndPrice.add(orders.getId());
            idDateAndPrice.add(orders.getDate());
            idDateAndPrice.add(orders.getPrice());
        });
        return new Response("success", true, idDateAndPrice);
    }
}
