package com.timlin.springboot_mall.dao;

import com.timlin.springboot_mall.constant.ProductCategory;
import com.timlin.springboot_mall.model.Product;
import com.timlin.springboot_mall.others.ProductQueryParams;
import com.timlin.springboot_mall.others.ProductRequest;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);

}
