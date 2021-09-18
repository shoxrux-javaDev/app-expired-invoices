package com.javadev.appexpiredinvoices.entity.template;


import com.javadev.appexpiredinvoices.entity.Customer;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.Instant;
@MappedSuperclass
@Data
public class AbsMain {

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    @CreatedBy
    @JoinColumn(updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer createdBy;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer updatedBy;

}
