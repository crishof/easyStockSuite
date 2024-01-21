package com.crishof.brandsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BrandSvApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrandSvApplication.class, args);
    }

}
