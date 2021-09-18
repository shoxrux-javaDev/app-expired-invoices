package com.javadev.appexpiredinvoices.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class DetailDto {

    private UUID OrderId;

    private UUID ProductId;

    private short quantity;
}
