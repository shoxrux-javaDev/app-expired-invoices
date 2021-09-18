package com.javadev.appexpiredinvoices.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class OrdersDto {

    private UUID customerId;
}
