package com.timlin.springboot_mall.dao;

import com.timlin.springboot_mall.dto.CreateOrderRequest;
import com.timlin.springboot_mall.model.Order;
import com.timlin.springboot_mall.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId, Integer totalAmount);
    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    Order getOrderById(Integer orderId);
    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
}
