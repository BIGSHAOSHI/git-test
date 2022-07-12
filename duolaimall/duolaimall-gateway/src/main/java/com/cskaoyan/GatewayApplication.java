package com.cskaoyan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.TimeZone;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        // 设置jvm时间：将时间改为第8时区的时间
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        SpringApplication.run(GatewayApplication.class, args);
    }
}
