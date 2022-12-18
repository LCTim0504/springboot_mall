package com.timlin.springboot_mall.dao;

import com.timlin.springboot_mall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
