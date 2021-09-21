package com.javadev.appexpiredinvoices.repo;


import com.javadev.appexpiredinvoices.entity.Orders;
import com.javadev.appexpiredinvoices.repo.projection.CustomerLastOrderProjection;
import com.javadev.appexpiredinvoices.repo.projection.GetCustomerWithoutOrders;
import com.javadev.appexpiredinvoices.repo.projection.OrderInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Orders, UUID> {

    @Query(value = "select o.id,o.createdAt,o.customer.id from Orders o left outer join Detail d on o.id=d.id" +
            " where  o.createdAt < '2016-09-0600:00:00' and d.orders.id is null order by o.id asc")
    List<Orders> ordersWithoutDetails();//3-task

    @Query(value = "select c.id as id,c.username as name,c.country as cty,c.address as res,c.phone as phone " +
            "from Orders o join customer c on c.id=o.customer_id_id where o.date not between " +
            "'2016-01-01T00:00:00' and '2016-12-31T00:00:00'", nativeQuery = true)
    List<GetCustomerWithoutOrders> customersWithoutOrders();//4-section

    @Query(value = "select o.customer_id_id as id,c.name as name,max(o.date) as date from orders o join customer c on c.id=o.customer_id_id" +
            " group by o.customer_id_id, c.name,c.name having count(o.customer_id_id)>1 order by o.customer_id_id", nativeQuery = true)
    List<CustomerLastOrderProjection> customerLastOrders();//5-task

    @Query(value = "select count(o.id) from Orders o join Customer c on c.id = o.customer_id_id " +
            " where o.created_at between '2016-01-01 00:00:00' and '2016-12-31 00:00:00'", nativeQuery = true)
    Integer numberOfProductInYear();//9-task

    @Query(value = "select o.id, o.created_at,sum(d.quantity*p.price) as price from orders o join detail d " +
            " on o.id = d.order_id_id join product p on p.id = d.product_id_id " +
            " where not exists (select i from invoice i where o.id = i.order_id_id) group by o.id, o.created_at", nativeQuery = true)
    List<OrderInterface> ordersWithoutInvoices();//10-task
}
