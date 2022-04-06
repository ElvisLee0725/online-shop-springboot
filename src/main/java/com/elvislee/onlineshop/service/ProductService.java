package com.elvislee.onlineshop.service;

import com.elvislee.onlineshop.dto.ProductQueryParams;
import com.elvislee.onlineshop.dto.ProductRequest;
import com.elvislee.onlineshop.model.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getProducts(ProductQueryParams productQueryParams);

    public Product getProductById(Integer productId);

    public Integer createProduct(ProductRequest productRequest);

    public void updateProduct(Integer productId, ProductRequest productRequest);

    public void deleteProductById(Integer productId);
}
