package com.javadev.appexpiredinvoices.repo.projection;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public interface OrderInterface {

    UUID id();

    Instant createdAt();

    UUID customerId();


}
