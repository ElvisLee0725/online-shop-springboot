package com.elvislee.onlineshop.service;

import com.elvislee.onlineshop.dto.ProductRequest;
import com.elvislee.onlineshop.model.Product;

public interface ProductService {
    public Product getProductById(Integer productId);

    public Integer createProduct(ProductRequest productRequest);
}
