package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/kafka")
@Slf4j
public class KafkaController {

    @Value("${kafka.topic.log}")
    private String topic;

    @GetMapping("/topic")
    public String test(){
        return "this is " + topic;
    }

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/redis/{key}")
    public String test2(@PathVariable(value = "key") String key){
        Random r = new Random(1);
        int ran1 = r.nextInt(100);
        String value = "value" + ran1;
        redisTemplate.opsForValue().set(key, value);
        log.info("---------{}---------{}",key,value);
        return (String) redisTemplate.opsForValue().get(key);
    }
}
