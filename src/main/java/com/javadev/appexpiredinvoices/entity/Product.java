package com.javadev.appexpiredinvoices.entity;

import com.javadev.appexpiredinvoices.entity.template.AbsUUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
public class Product extends AbsUUID {

    @Column(nullable = false)
    @Size(min = 10, max = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Category category;

    @Size(min = 20, max = 50)
    @Column
    private String description;

    @Column(nullable = false)
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment attachment;
}
