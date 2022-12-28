package com.timlin.springboot_mall.service.impl;

import com.timlin.springboot_mall.dao.UserDao;
import com.timlin.springboot_mall.dto.UserLoginRequest;
import com.timlin.springboot_mall.dto.UserRegisterRequest;
import com.timlin.springboot_mall.model.User;
import com.timlin.springboot_mall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        //檢查信箱
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null) {
            log.warn("該 email {} 已被用戶 {} 註冊", userRegisterRequest.getEmail(), user.getUserId());

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //Hash password
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword((hashedPassword));

        //創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {

        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        //檢查user是否存在
        if (user == null) {
            log.warn("該 email {} 尚未被註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //Hash password
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        //比較密碼 (比較兩個string時，使用equals而不能用'==')
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        } else {
            log.warn("email {} 密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}


