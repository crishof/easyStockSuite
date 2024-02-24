package com.crishof.categorysv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CategorySvApplication {

    public static void main(String[] args) {
        SpringApplication.run(CategorySvApplication.class, args);
    }

}
