package org.crishof.supplierpricelistsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class SupplierPriceListSvApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupplierPriceListSvApplication.class, args);
    }

}
