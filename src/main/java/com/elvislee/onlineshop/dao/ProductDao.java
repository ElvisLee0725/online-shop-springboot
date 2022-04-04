package com.elvislee.onlineshop.dao;

import com.elvislee.onlineshop.dto.ProductRequest;
import com.elvislee.onlineshop.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getProducts();

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
