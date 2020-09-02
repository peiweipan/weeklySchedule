package com.weekly;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @version V1.0
 * @author: csz
 * @Title
 * @Package: com.weekly
 * @Description:
 * @date: 2020/09/02
 */
@SpringBootApplication(scanBasePackages = "com.weekly")
@MapperScan("com.weekly.*.mapper")
public class AppServer {
    public static void main(String[] args) {
        SpringApplication.run(AppServer.class, args);
    }
}
