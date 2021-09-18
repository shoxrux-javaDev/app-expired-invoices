package com.javadev.appexpiredinvoices.repo;

import com.javadev.appexpiredinvoices.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepo extends JpaRepository<Product, UUID> {

    @Query(value = "select * from product p join detail d on p.id=d.product_id_id" +
            " where d.quantity>8 order by p.created_at", nativeQuery = true)
    List<Product> bulkProduct();//8-task

    @Query(value = "select sum(d.quantity*p.price) from product p join detail d on p.id=d.product_id_id" +
            " where d.order_id_id=?1", nativeQuery = true)
    double TotalPriceOfProduct(UUID id);

}
