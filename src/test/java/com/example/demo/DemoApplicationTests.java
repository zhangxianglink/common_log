package com.example.demo;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


class DemoApplicationTests {

    @Test
    public void contextLoads() throws Exception {
        String pwd = "123456";
        String[] split = pwd.split("");
        ConfigTools.main(split);

    }

}
