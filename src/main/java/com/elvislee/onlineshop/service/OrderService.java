package com.elvislee.onlineshop.service;

import com.elvislee.onlineshop.dto.CreateOrderRequest;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
