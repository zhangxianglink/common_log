package com.example.demo.controller;

import com.example.demo.dao.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/400")
    public String error400(){
        return "客户端请求的语法错误";
    }

    @GetMapping("/401")
    public String error401(){
        return "要求用户的身份认证";
    }

    @GetMapping("/403")
    public String error403(){
        return "用户不足权限访问，理解请求客户端的请求，但是拒绝执行此请求";
    }

    @GetMapping("/404")
    public String error404(){
        return "服务器无法根据客户端的请求找到资源";
    }

    @GetMapping("/415")
    public String error415(){
        return "服务器无法处理请求附带的媒体格式";
    }
}
