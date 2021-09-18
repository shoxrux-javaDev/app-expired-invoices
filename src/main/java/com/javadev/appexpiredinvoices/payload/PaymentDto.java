package com.javadev.appexpiredinvoices.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class PaymentDto {

    @NotBlank(message = "amount don't be empty")
    private double amount;

    @NotBlank(message = "invoice don't be empty")
    private UUID invoiceId;
}
