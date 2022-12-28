package com.timlin.springboot_mall.controller;

import com.timlin.springboot_mall.dao.UserDao;
import com.timlin.springboot_mall.dto.ProductRequest;
import com.timlin.springboot_mall.dto.UserLoginRequest;
import com.timlin.springboot_mall.dto.UserRegisterRequest;
import com.timlin.springboot_mall.model.Product;
import com.timlin.springboot_mall.model.User;
import com.timlin.springboot_mall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<User> createProduct(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {

        Integer userId = userService.register(userRegisterRequest);
        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @PostMapping("/users/login")
    public ResponseEntity<User> login (@RequestBody @Valid UserLoginRequest userLoginRequest) {

        User user = userService.login(userLoginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
