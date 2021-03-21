package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisClient(){
       redisTemplate.opsForValue().set("hello","world");
        Object foo = redisTemplate.opsForValue().get("hello");
        System.out.println(foo);
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.flushDb();
    }
}
