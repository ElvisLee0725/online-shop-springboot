package com.elvislee.onlineshop.service;

import com.elvislee.onlineshop.dto.CreateOrderRequest;
import com.elvislee.onlineshop.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
