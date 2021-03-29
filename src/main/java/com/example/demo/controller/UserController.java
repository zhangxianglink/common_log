package com.example.demo.controller;

import com.example.demo.annotation.ThreadPoolStatus;
import com.example.demo.dao.UserMapper;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor =@_(@Autowired))
public class UserController {

    private final UserMapper userMapper;

    @Resource(name = "demoAsyncServer")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @GetMapping("/admin")
    public String admin(){
        List<User> userdao = userMapper.selectAllUser();
        return userdao.toString();
    }


    @ThreadPoolStatus("t1--------------")
    @GetMapping("/t1")
    public void t1(){
        threadPoolTaskExecutor.execute(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("UserController ti ------------");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @ThreadPoolStatus("t2--------------")
    @GetMapping("/t2")
    public String t2() throws ExecutionException, InterruptedException {
        Future<String> submit = threadPoolTaskExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                int a = 0;
                for (int i = 0; i < 10; i++) {
                    a += i;
                }
                return "this is callback " + a;
            }
        });
        String s = submit.get();
        return s;
    }






}
