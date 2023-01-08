package com.timlin.springboot_mall.service;

import com.timlin.springboot_mall.dto.CreateOrderRequest;
import com.timlin.springboot_mall.dto.OrderQueryParams;
import com.timlin.springboot_mall.model.Order;

import java.util.List;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
    Order getOrderById(Integer orderId);
    Integer countOrder(OrderQueryParams orderQueryParams);
    List<Order> getOrders(OrderQueryParams orderQueryParams);
}
