package com.javadev.appexpiredinvoices.repo.projection;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface PaymentProjection {

    UUID invoiceId();

    double column();
}
