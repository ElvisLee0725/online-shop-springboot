package com.elvislee.onlineshop.dao;

import com.elvislee.onlineshop.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
