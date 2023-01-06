package com.timlin.springboot_mall.service;

import com.timlin.springboot_mall.dto.CreateOrderRequest;
import com.timlin.springboot_mall.model.Order;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
    Order getOrderById(Integer orderId);
}
