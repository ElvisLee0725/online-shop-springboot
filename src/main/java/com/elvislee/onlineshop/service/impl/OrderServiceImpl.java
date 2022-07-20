package com.elvislee.onlineshop.service.impl;

import com.elvislee.onlineshop.dao.OrderDao;
import com.elvislee.onlineshop.dao.ProductDao;
import com.elvislee.onlineshop.dao.UserDao;
import com.elvislee.onlineshop.dto.BuyItem;
import com.elvislee.onlineshop.dto.CreateOrderRequest;
import com.elvislee.onlineshop.model.Order;
import com.elvislee.onlineshop.model.OrderItem;
import com.elvislee.onlineshop.model.Product;
import com.elvislee.onlineshop.model.User;
import com.elvislee.onlineshop.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        User user = userDao.getUserById(userId);

        if(user == null) {
            log.warn("This user id {} does not exist", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            if(product == null) {
                log.warn("The product id {} does not exist", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            else if(product.getStock() < buyItem.getQuantity()) {
                log.warn("Product id {} doesn't have enough stock. Cancel order. Stock: {}, Purchase Quantity: {}", buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount += amount;

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
        order.setOrderItemList(orderItemList);

        return order;
    }
}
