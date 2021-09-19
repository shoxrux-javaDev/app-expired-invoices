package com.javadev.appexpiredinvoices.repo.projection;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface GetBulkProduct {

    UUID getId();

    double getPrice();
}
