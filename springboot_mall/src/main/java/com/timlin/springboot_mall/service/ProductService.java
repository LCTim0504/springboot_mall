package com.timlin.springboot_mall.service;

import com.timlin.springboot_mall.model.Product;
import com.timlin.springboot_mall.others.ProductRequest;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
}
