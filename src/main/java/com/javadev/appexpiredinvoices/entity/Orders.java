package com.javadev.appexpiredinvoices.entity;

import com.javadev.appexpiredinvoices.entity.template.AbsUUID;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Orders extends AbsUUID {

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Customer customer;


}
