package com.javadev.appexpiredinvoices.repo;

import com.javadev.appexpiredinvoices.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, UUID> {

    @Query(value = "select * from invoice i where i.due_date < now()", nativeQuery = true)
    List<Invoice> getAllExpired();//1-task

    @Query(value = "select * from invoice i join orders o on o.id = i.order_id_id" +
            " where i.created_at <= o.created_at",nativeQuery = true)
    List<Invoice> getWrongDateInvoices();//2-task

}