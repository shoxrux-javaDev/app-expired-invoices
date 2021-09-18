package com.javadev.appexpiredinvoices.repo;


import com.javadev.appexpiredinvoices.entity.Orders;
import com.javadev.appexpiredinvoices.repo.projection.OrderInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Orders, UUID> {

    @Query(value = "select o.id,o.createdAt,o.customer.id from Orders o left outer join Detail d on o.id=d.id" +
            " where  o.createdAt < '2016-09-0600:00:00' and d.orders.id is null order by o.id asc")
    List<Orders> ordersWithoutDetails();//3-task

    @Query(value = "select * from Orders o join customer c  on c.id=o.customer_id_id " +
            "where o.date not between '2016-01-01T00:00:00' and '2016-12-31T00:00:00'", nativeQuery = true)
    List<Orders> customersWithoutOrders();//4-section

    @Query(value = "select * from orders o inner join customer c on c.id = o.customer_id_id" +
            " where c.id = ?1 and (o.created_at between ?2 and ?3) " +
            "order by o.created_at desc limit 1", nativeQuery = true)
    Orders customerLastOrders(UUID id, LocalDateTime start, LocalDateTime end);//5-task

    @Query(value = "select count(o.id) from Orders o join Customer c on c.id = o.customer_id_id " +
            " where o.created_at between '2016-01-01 00:00:00' and '2016-12-31 00:00:00'", nativeQuery = true)
    Integer numberOfProductInYear();//9-task

    @Query(value = "select o.id,o.date,o.customer_id_id from orders o left outer join invoice i on o.id = i.order_id_id " +
            "where i.order_id_id is null " +
            "intersect " +
            "select o.id,o.date,o.customer_id_id from detail d join orders o on o.id = d.order_id_id ", nativeQuery = true)
    List<OrderInterface> ordersWithoutInvoices();//10-task
}
