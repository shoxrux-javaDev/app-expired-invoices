package com.javadev.appexpiredinvoices.entity;

import com.javadev.appexpiredinvoices.entity.template.AbsUUID;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Invoice extends AbsUUID {

    @Column(nullable = false)
    private double amount;

    @Timestamp
    @Column(nullable = false,updatable = false)
    private Instant dueDate;

    @OneToOne(fetch = FetchType.LAZY)
    private Orders orders;
}
