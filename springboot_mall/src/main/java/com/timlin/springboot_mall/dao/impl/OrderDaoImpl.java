package com.timlin.springboot_mall.dao.impl;

import com.timlin.springboot_mall.dao.OrderDao;
import com.timlin.springboot_mall.dto.OrderQueryParams;
import com.timlin.springboot_mall.model.Order;
import com.timlin.springboot_mall.model.OrderItem;
import com.timlin.springboot_mall.rowmapper.OrderItemRowMapper;
import com.timlin.springboot_mall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order`" +
                "(user_id," +
                "total_amount," +
                "created_date," +
                "last_modified_date)" +
                "VALUES" +
                "(:user_id," +
                ":total_amount," +
                ":created_date," +
                ":last_modified_date)";

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("total_amount", totalAmount);

        Date now = new Date();
        map.put("created_date", now);
        map.put("last_modified_date", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        String sql = "INSERT INTO order_item (order_id, product_id, quantity, amount) " +
                "VALUES( :order_id, :product_id, :quantity, :amount)";

        //一筆筆update效率較低
//        for(OrderItem orderItem: orderItemList) {
//
//            Map<String, Object> map = new HashMap<>();
//            map.put("order_id", orderId);
//            map.put("product_id", orderItem.getProductId());
//            map.put("quantity", orderItem.getQuantity());
//            map.put("amount", orderItem.getAmount());
//            namedParameterJdbcTemplate.update(sql, map);
//        }

        //一次update效率較高
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("order_id", orderId);
            parameterSources[i].addValue("product_id", orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("amount", orderItem.getAmount());
        }
        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "select * from `order` where order_id = :orderId";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        return orderList.size() > 0 ? orderList.get(0) : null;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = "select * from order_item as oi left join product as p " +
                "on oi.product_id = p.product_id where oi.order_id = :orderId";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());

        return orderItemList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        String sql = "select count(*) from `order` where 1 = 1 ";

        Map<String, Object> map = new HashMap<>();

        sql = addFilteringSql(sql, map, orderQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        String sql = "select * from `order` where 1 = 1 ";

        Map<String, Object> map = new HashMap<>();

        // 條件篩選
        sql = addFilteringSql(sql, map, orderQueryParams);
        //排序
        sql += " order by created_date desc";
        //分頁
        sql += " limit :limit offset :offset";

        map.put("limit", orderQueryParams.getLimit());
        map.put("offset", orderQueryParams.getOffset());

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
        return orderList;
    }

    private String addFilteringSql(String sql, Map<String, Object> map, OrderQueryParams orderQueryParams) {

        if (orderQueryParams.getUserId() != null) {
            sql += " and user_id = :user_id";
            map.put("user_id", orderQueryParams.getUserId());
        }
        return sql;
    }
}
