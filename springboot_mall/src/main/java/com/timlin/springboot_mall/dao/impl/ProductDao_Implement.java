package com.timlin.springboot_mall.dao.impl;

import com.timlin.springboot_mall.dao.ProductDao;
import com.timlin.springboot_mall.model.Product;
import com.timlin.springboot_mall.dto.ProductQueryParams;
import com.timlin.springboot_mall.dto.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.timlin.springboot_mall.rowmapper.ProductRowMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDao_Implement implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql = "select * from product where 1=1";

        Map<String, Object> map = new HashMap<>();

        //篩選
        sql = filteringSQL(sql, map, productQueryParams);

        //排序
        sql += " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        //分頁
        sql += " LIMIT :limit OFFSET :offset";
        map.put("limit", productQueryParams.getLimit());
        map.put("offset", productQueryParams.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList;
    }

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String sql = "select count(*) from product where 1=1";

        Map<String, Object> map = new HashMap<>();

        //篩選
        sql = filteringSQL(sql, map, productQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    private String filteringSQL(String sql, Map<String, Object> map, ProductQueryParams productQueryParams) {

        if (productQueryParams.getCategory() != null) {
            sql += " AND category = :category";
            map.put("category", productQueryParams.getCategory().name());
        }
        if (productQueryParams.getSearch() != null) {
            sql += " AND product_name LIKE :search";
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }

        return sql;
    }

    @Override
    public Product getProductById(Integer productId) {

        String sql = "select * from product where product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList.size() != 0 ? productList.get(0) : null;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product" +
                "(product_name," +
                "category," +
                "image_url," +
                "price," +
                "stock," +
                "description," +
                "created_date," +
                "last_modified_date)" +
                "VALUES" +
                "(:product_name," +
                ":category," +
                ":image_url," +
                ":price," +
                ":stock," +
                ":description," +
                ":created_date," +
                ":last_modified_date)";

        Map<String, Object> map = new HashMap<>();
        map.put("product_name", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString()); //!!!!!!
        map.put("image_url", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("created_date", now);
        map.put("last_modified_date", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        String sql = "UPDATE product SET " +
                "product_name = :product_name," +
                "category = :category," +
                "image_url = :image_url," +
                "price =:price," +
                "stock = :stock," +
                "description = :description," +
                "last_modified_date = :last_modified_date " +
                "WHERE product_id = :product_id";

        Map<String, Object> map = new HashMap<>();
        map.put("product_id", productId);
        map.put("product_name", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString()); //!!!!!!
        map.put("image_url", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("last_modified_date", now);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProductById(Integer productId) {

        String sql = "delete from product where product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }
}
