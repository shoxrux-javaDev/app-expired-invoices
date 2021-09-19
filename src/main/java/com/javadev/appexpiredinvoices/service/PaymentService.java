package com.javadev.appexpiredinvoices.service;

import com.javadev.appexpiredinvoices.entity.Invoice;
import com.javadev.appexpiredinvoices.entity.Payment;
import com.javadev.appexpiredinvoices.payload.PaymentDto;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.repo.InvoiceRepo;
import com.javadev.appexpiredinvoices.repo.PaymentRepo;
import com.javadev.appexpiredinvoices.repo.projection.PaymentProjection;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentService {

    final PaymentRepo paymentRepo;
    final InvoiceRepo invoiceRepo;

    public PaymentService(PaymentRepo paymentRepo, InvoiceRepo invoiceRepo) {
        this.paymentRepo = paymentRepo;
        this.invoiceRepo = invoiceRepo;
    }

    public Response addPayment(PaymentDto paymentDto) {
        Optional<Invoice> invoiceOptional = invoiceRepo.findById(paymentDto.getInvoiceId());
        if (invoiceOptional.isEmpty()) return new Response("invoice is not found", false);
        Payment payment = new Payment();
        payment.setAmount(paymentDto.getAmount());
        payment.setInvoice(invoiceOptional.get());
        paymentRepo.save(payment);
        return new Response("added payment", true);
    }

    public Response getPayment(UUID id) {
        Optional<Payment> paymentOptional = paymentRepo.findById(id);
        if (paymentOptional.isEmpty()) return new Response("payment not found", false);
        return new Response("success", true, paymentOptional.get());
    }

    public Response getPayments() {
        List<Payment> paymentList = paymentRepo.findAll();
        if (paymentList.isEmpty()) return new Response("payment list is empty", false);
        return new Response("success", true, paymentList);
    }

    public Response deletePayment(UUID id) {
        Optional<Payment> paymentOptional = paymentRepo.findById(id);
        if (paymentOptional.isEmpty()) return new Response("payment not found", false);
        paymentRepo.deleteById(id);
        return new Response("deleted", true);
    }

    public Response deleteALlPayment() {
        paymentRepo.deleteAll();
        return new Response("deleted all", true);
    }

    public Response getOverPaidInvoice() {
        List<PaymentProjection> allIssueInvoices = paymentRepo.getAllIssueInvoices();
        Map<UUID,Double> overPaidInvoice = new LinkedHashMap<>();
        if (allIssueInvoices.isEmpty()) return new Response("paymentList is empty", true);
        for (PaymentProjection paymentProjection : allIssueInvoices) {
            overPaidInvoice.put(paymentProjection.invoiceId(), paymentProjection.overpaid());
        }
        return new Response("success", true, overPaidInvoice);
    }
}
