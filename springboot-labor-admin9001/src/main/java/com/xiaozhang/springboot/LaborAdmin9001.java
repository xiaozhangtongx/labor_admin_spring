package com.xiaozhang.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: xiaozhangtx
 * @ClassName: LaborAdmin9001
 * @Description: TODO 服务启动类
 * @date: 2023/2/27 17:08
 * @Version: 1.0
 */
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class LaborAdmin9001 {
    public static void main(String[] args) {
        SpringApplication.run(LaborAdmin9001.class, args);
    }
}
