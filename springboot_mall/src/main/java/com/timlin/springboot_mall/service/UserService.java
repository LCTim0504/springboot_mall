package com.timlin.springboot_mall.service;

import com.timlin.springboot_mall.dto.UserRegisterRequest;
import com.timlin.springboot_mall.model.User;

public interface UserService {

    User getUserById(Integer userId);
    Integer register(UserRegisterRequest userRegisterRequest);
}
