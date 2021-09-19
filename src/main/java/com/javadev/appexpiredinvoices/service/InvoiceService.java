package com.javadev.appexpiredinvoices.service;

import com.javadev.appexpiredinvoices.entity.Customer;
import com.javadev.appexpiredinvoices.entity.Invoice;
import com.javadev.appexpiredinvoices.entity.Orders;
import com.javadev.appexpiredinvoices.payload.InvoiceDto;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.repo.InvoiceRepo;
import com.javadev.appexpiredinvoices.repo.OrderRepo;
import com.javadev.appexpiredinvoices.repo.projection.GetWrongDate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InvoiceService {
    final InvoiceRepo invoiceRepo;
    final OrderRepo orderRepo;

    public InvoiceService(InvoiceRepo invoiceRepo, OrderRepo orderRepo) {
        this.invoiceRepo = invoiceRepo;
        this.orderRepo = orderRepo;
    }

    public Response addInvoice(InvoiceDto invoiceDto) {
        Optional<Orders> ordersOptional = orderRepo.findById(invoiceDto.getOrderId());
        if (ordersOptional.isEmpty()) return new Response("this order not found", false);
        Invoice invoice = new Invoice();
        invoice.setAmount(invoiceDto.getAmount());
        invoice.setDueDate(invoiceDto.getDueDate());
        invoice.setOrders(ordersOptional.get());
        invoiceRepo.save(invoice);
        return new Response("saved invoice", true);
    }

    public Response addAllInvoice(List<InvoiceDto> invoiceDtoList) {
        for (InvoiceDto invoiceDto : invoiceDtoList) {
            Optional<Orders> ordersOptional = orderRepo.findById(invoiceDto.getOrderId());
            if (ordersOptional.isEmpty()) return new Response("this order not found", false);
            Invoice invoice = new Invoice();
            invoice.setAmount(invoiceDto.getAmount());
            invoice.setDueDate(invoiceDto.getDueDate());
            invoice.setOrders(ordersOptional.get());
            invoiceRepo.save(invoice);
        }
        return new Response("saved invoice", true);
    }

    public Response getInvoice(UUID id) {
        Optional<Invoice> invoiceOptional = invoiceRepo.findById(id);
        if (invoiceOptional.isEmpty()) return new Response("invoice not found", false);
        return new Response("get invoice", true, invoiceOptional.get());
    }

    public Response getAllInvoice() {
        List<Invoice> invoiceList = invoiceRepo.findAll();
        if (invoiceList.isEmpty()) return new Response("invoiceList is empty", false);
        return new Response("get all invoices", true, invoiceList);
    }

    public Response deleteOneInvoice(UUID id) {
        Optional<Invoice> invoiceOptional = invoiceRepo.findById(id);
        if (invoiceOptional.isEmpty()) return new Response("not found", false);
        invoiceRepo.deleteById(id);
        return new Response("deleted", true);
    }

    public Response deleteAllInvoices() {
        invoiceRepo.deleteAll();
        return new Response("deleted all", true);
    }

    public Response getExpiredIv() {
        List<Invoice> invoiceList = invoiceRepo.getAllExpired();
        if (invoiceList.isEmpty()) return new Response("invoice list is empty", false);
        return new Response("success", true, invoiceList);
    }

    public Response getWrongInvoice() {
        List<Object> wrongInvoiceInfo = new ArrayList<>();
        List<GetWrongDate> invoiceList = invoiceRepo.getWrongDateInvoices();
        if (invoiceList.isEmpty()) return new Response("invoice is empty", false);
        for (GetWrongDate getWrongDate : invoiceList) {
            wrongInvoiceInfo.add(getWrongDate.getInvoiceId());
            wrongInvoiceInfo.add(getWrongDate.getDateInv());
            wrongInvoiceInfo.add(getWrongDate.getOrderId());
            wrongInvoiceInfo.add(getWrongDate.getDateOrd());
        }
        return new Response("success", true, wrongInvoiceInfo);
    }

}
