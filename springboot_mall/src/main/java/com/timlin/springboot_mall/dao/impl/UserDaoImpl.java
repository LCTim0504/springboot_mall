package com.timlin.springboot_mall.dao.impl;

import com.timlin.springboot_mall.dao.UserDao;
import com.timlin.springboot_mall.dto.UserRegisterRequest;
import com.timlin.springboot_mall.model.Product;
import com.timlin.springboot_mall.model.User;
import com.timlin.springboot_mall.rowmapper.ProductRowMapper;
import com.timlin.springboot_mall.rowmapper.UserRowMapper;
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
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User getUserById(Integer userId) {
        String sql = "select user_id, email, password, created_date, last_modified_date " +
                "from `user` where user_id = :user_id";

        Map<String, Object> map = new HashMap<>();

        map.put("user_id", userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        return userList.size() > 0 ? userList.get(0) : null;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "select user_id, email, password, created_date, last_modified_date " +
                "from `user` where email = :email";

        Map<String, Object> map = new HashMap<>();

        map.put("email", email);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        return userList.size() > 0 ? userList.get(0) : null;
    }

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "insert into `user` (email, password, created_date, last_modified_date) "
                + "VALUES (:email, :password, :created_date, :last_modified_date)";

        Map<String, Object> map = new HashMap<>();

        map.put("email", userRegisterRequest.getEmail());
        map.put("password", userRegisterRequest.getPassword());
        Date now = new Date();
        map.put("created_date", now);
        map.put("last_modified_date", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int userId = keyHolder.getKey().intValue();

        return userId;
    }
}
