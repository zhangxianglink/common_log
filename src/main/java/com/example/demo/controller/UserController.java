package com.example.demo.controller;

import com.example.demo.dao.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/admin")
    public String admin(){
        List<User> userdao = userMapper.selectAllUser();
        return userdao.toString();
    }

    @GetMapping("/user")
    public String user(){
        return "用户权限访问";
    }
}
