package com.timlin.springboot_mall.dao;

import com.timlin.springboot_mall.model.Product;
import com.timlin.springboot_mall.dto.ProductQueryParams;
import com.timlin.springboot_mall.dto.ProductRequest;

import java.util.List;

public interface ProductDao {
    List<Product> getProducts(ProductQueryParams productQueryParams);
    Integer countProduct(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
    void updateStock(Integer productId, Integer stock);

}
