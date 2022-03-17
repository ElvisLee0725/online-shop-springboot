package com.elvislee.onlineshop.service.impl;

import com.elvislee.onlineshop.dao.ProductDao;
import com.elvislee.onlineshop.model.Product;
import com.elvislee.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
