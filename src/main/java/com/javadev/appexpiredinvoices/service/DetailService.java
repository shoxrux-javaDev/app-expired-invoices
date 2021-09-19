package com.javadev.appexpiredinvoices.service;


import com.javadev.appexpiredinvoices.entity.Detail;
import com.javadev.appexpiredinvoices.entity.Orders;
import com.javadev.appexpiredinvoices.entity.Product;
import com.javadev.appexpiredinvoices.payload.DetailDto;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.repo.DetailRepo;
import com.javadev.appexpiredinvoices.repo.OrderRepo;
import com.javadev.appexpiredinvoices.repo.ProductRepo;
import com.javadev.appexpiredinvoices.repo.projection.GetBulkProduct;
import com.javadev.appexpiredinvoices.repo.projection.HighDemandProductProjection;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class DetailService {
    final DetailRepo detailRepo;
    final OrderRepo orderRepo;
    final ProductRepo productRepo;

    public DetailService(DetailRepo detailRepo,
                         OrderRepo orderRepo,
                         ProductRepo productRepo) {
        this.detailRepo = detailRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public Response addDetail(DetailDto detailDto) {
        Optional<Orders> ordersOptional = orderRepo.findById(detailDto.getOrderId());
        if (ordersOptional.isEmpty()) return new Response("it is order not found", false);
        Optional<Product> productOptional = productRepo.findById(detailDto.getProductId());
        if (productOptional.isEmpty()) return new Response("it is product not found", false);
        Detail detail = new Detail();
        detail.setOrders(ordersOptional.get());
        detail.setProduct(productOptional.get());
        detail.setQuantity(detailDto.getQuantity());
        detailRepo.save(detail);
        return new Response("order saved successfully", true);
    }

    public Response addAllDetail(List<DetailDto> detailDtoList) {
        for (DetailDto detailDto : detailDtoList) {
            Optional<Orders> optionalOrders = orderRepo.findById(detailDto.getOrderId());
            if (optionalOrders.isEmpty()) return new Response("it is order not found", false);
            Optional<Product> productOptional = productRepo.findById(detailDto.getProductId());
            if (productOptional.isEmpty()) return new Response("it is product not found", false);
            Detail detail = new Detail();
            detail.setOrders(optionalOrders.get());
            detail.setProduct(productOptional.get());
            detail.setQuantity(detailDto.getQuantity());
            detailRepo.save(detail);
        }
        return new Response("order saved successfully", true);
    }

    public Response getDetail(UUID id) {
        Optional<Detail> detailOptional = detailRepo.findById(id);
        if (detailOptional.isEmpty()) return new Response("detail info not found", false);
        return new Response("detail found", true, detailOptional.get());
    }

    public Response getAllDetail() {
        List<Detail> detailList = detailRepo.findAll();
        if (detailList.isEmpty()) return new Response("details not found", false);
        return new Response("orders found", true, detailList);
    }

    public Response deleteDetail(UUID id) {
        Optional<Detail> optionalDetail = detailRepo.findById(id);
        if (optionalDetail.isEmpty()) return new Response("detail not found", false);
        detailRepo.deleteById(id);
        return new Response("successfully delete", true);
    }

    public Response deleteAllDetail() {
        detailRepo.deleteAll();
        return new Response("delete all", true);
    }

    public Response getHighDemandProducts() {
        List<HighDemandProductProjection> highDemandProducts = detailRepo.highDemandProducts();
        if (highDemandProducts.isEmpty()) return new Response("detail is empty", false);
        Map<UUID, Integer> highDemand = new LinkedHashMap<>();
        for (HighDemandProductProjection highDemandProduct : highDemandProducts) {
            highDemand.put(highDemandProduct.getId(), highDemandProduct.getCount());
        }
        return new Response("success", true, highDemand);
    }

    public Response getBulkProductPrice() {
        List<GetBulkProduct> detailList = productRepo.bulkProduct();
        if (detailList.isEmpty()) return new Response("detail product list is empty", false);
        Map<UUID, Double> idAndPrice = new LinkedHashMap<>();
        for (GetBulkProduct getBulkProduct : detailList) {
            idAndPrice.put(getBulkProduct.getId(), getBulkProduct.getPrice());
        }
        return new Response("success", true, idAndPrice);
    }
}
