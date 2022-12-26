package com.timlin.springboot_mall.dao;

import com.timlin.springboot_mall.dto.UserRegisterRequest;
import com.timlin.springboot_mall.model.User;

public interface UserDao {

    User getUserById(Integer userId);
    Integer createUser(UserRegisterRequest userRegisterRequest);

}
