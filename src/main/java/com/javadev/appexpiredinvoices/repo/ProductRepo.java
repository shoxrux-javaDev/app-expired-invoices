package com.javadev.appexpiredinvoices.repo;

import com.javadev.appexpiredinvoices.entity.Product;
import com.javadev.appexpiredinvoices.repo.projection.GetBulkProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepo extends JpaRepository<Product, UUID> {

    @Query(value = "select p.id as id,p.price as price from product p join detail d on p.id=d.product_id_id" +
            " where d.quantity>8 order by p.created_at", nativeQuery = true)
    List<GetBulkProduct> bulkProduct();//8-task

}
