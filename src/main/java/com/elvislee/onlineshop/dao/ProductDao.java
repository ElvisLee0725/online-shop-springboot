package com.elvislee.onlineshop.dao;

import com.elvislee.onlineshop.dto.ProductRequest;
import com.elvislee.onlineshop.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
