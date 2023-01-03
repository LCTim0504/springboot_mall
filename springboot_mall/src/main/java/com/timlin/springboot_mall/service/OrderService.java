package com.timlin.springboot_mall.service;

import com.timlin.springboot_mall.dto.CreateOrderRequest;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
