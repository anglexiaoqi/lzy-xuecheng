package com.xuecheng.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author lzy
 * @version 1.0
 * @date 2020/4/17 14:06
 */

@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain")//扫描实体类
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }
}
