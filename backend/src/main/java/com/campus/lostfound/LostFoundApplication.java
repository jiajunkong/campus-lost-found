package com.campus.lostfound;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot 启动类
 */
@SpringBootApplication
@MapperScan("com.campus.lostfound.mapper")
public class LostFoundApplication {
    public static void main(String[] args) {
        SpringApplication.run(LostFoundApplication.class, args);
        System.out.println("======== 校园失物招领平台启动成功 http://localhost:8080 ========");
    }
}
