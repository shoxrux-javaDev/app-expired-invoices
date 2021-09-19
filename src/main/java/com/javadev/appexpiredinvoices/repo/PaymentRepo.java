package com.javadev.appexpiredinvoices.repo;

import com.javadev.appexpiredinvoices.entity.Payment;
import com.javadev.appexpiredinvoices.repo.projection.PaymentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, UUID> {

    @Query(value = "select p.invoice_id_id,sum(p.amount)-i.amount as overpaid from payment p join invoice i on i.id=p.invoice_id_id" +
            " group by p.invoice_id_id,p.amount,i.amount having " +
            " p.amount<>i.amount and sum(p.amount)>i.amount order by p.invoice_id_id",nativeQuery = true)//section-6
    List<PaymentProjection> getAllIssueInvoices();

}
