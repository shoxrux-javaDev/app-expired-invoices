package com.javadev.appexpiredinvoices.repo;


import com.javadev.appexpiredinvoices.entity.Detail;
import com.javadev.appexpiredinvoices.repo.projection.HighDemandProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DetailRepo extends JpaRepository<Detail, UUID> {

    @Query(value = "select d.orders_id as id,count(*) as count from detail d join orders o on o.id=d.orders_id " +
            " group by d.orders_id, o.created_at having count(*)>10 order by o.created_at", nativeQuery = true)
    List<HighDemandProductProjection> highDemandProducts();//7-task


}
