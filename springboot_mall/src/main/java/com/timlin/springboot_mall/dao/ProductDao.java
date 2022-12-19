package com.timlin.springboot_mall.dao;

import com.timlin.springboot_mall.model.Product;
import com.timlin.springboot_mall.others.ProductRequest;

public interface ProductDao {

    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);

}
