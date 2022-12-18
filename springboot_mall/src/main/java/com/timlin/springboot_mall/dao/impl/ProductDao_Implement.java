package com.timlin.springboot_mall.dao.impl;

import com.timlin.springboot_mall.dao.ProductDao;
import com.timlin.springboot_mall.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import rowMapper.ProductRowMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDao_Implement implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {

        String sql = "select * from product where product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList != null ? productList.get(0) : null;
    }
}
