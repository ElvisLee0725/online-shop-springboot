package com.elvislee.onlineshop.service;

import com.elvislee.onlineshop.dto.CreateOrderRequest;
import com.elvislee.onlineshop.dto.OrderQueryParams;
import com.elvislee.onlineshop.model.Order;

import java.util.List;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);
}
