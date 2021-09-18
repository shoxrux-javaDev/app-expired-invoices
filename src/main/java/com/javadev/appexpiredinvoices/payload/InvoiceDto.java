package com.javadev.appexpiredinvoices.payload;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class InvoiceDto {

    private double amount;

    private Instant dueDate;

    private UUID orderId;

}
