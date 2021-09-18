package com.javadev.appexpiredinvoices.payload;

import lombok.Data;

@Data
public class CustomerEditDto {

    private String username;

    private String password;

    private String country;

    private String email;

    private String address;

    private String phone;
}
