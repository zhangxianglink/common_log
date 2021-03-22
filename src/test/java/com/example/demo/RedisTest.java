package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.utils.RedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void redisClient(){
       redisTemplate.opsForValue().set("hello","world");
        Object foo = redisTemplate.opsForValue().get("hello");
        System.out.println(foo);
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.flushDb();
    }

    @Test
    public void redisSerializable() throws JsonProcessingException {
        User user = new User();
        user.setId(1L);
        user.setPassword("redispwd");
        user.setLocked(1);
        user.setPhone("111111111111");
        String s = new ObjectMapper().writeValueAsString(user);
        redisTemplate.opsForValue().set("user2",s);
        System.out.println(redisTemplate.opsForValue().get("user2"));
    }

    @Test
    public void show(){

        try {
            redisTemplate.multi();
            redisUtils.set("name","jack");
            System.out.println(redisUtils.get("name"));
            redisTemplate.exec();
        }catch (Exception e){
            redisTemplate.discard();
        }


    }

}
