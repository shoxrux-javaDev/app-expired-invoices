package com.javadev.appexpiredinvoices.repo.projection;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface GetCustomerWithoutOrders {

    UUID getId();

    String getName();

    String getCty();

    String getRes();

    String getPhone();


}
