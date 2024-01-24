package com.crishof.orchestratorsv.service;

import com.crishof.orchestratorsv.dto.BrandDTO;
import com.crishof.orchestratorsv.repository.BrandAPIClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    BrandAPIClient brandAPIClient;

    @Override
    @CircuitBreaker(name = "brand-sv", fallbackMethod = "fallbackGetBrandInfo")
    @Retry(name = "brand-sv")
    public BrandDTO getBrandInfo(Long brandId) {
        BrandDTO brandDTO = brandAPIClient.getBrandInfo(brandId);

//        createException();

        return brandDTO;
    }

    public BrandDTO fallbackGetBrandInfo(Throwable throwable) {
        return new BrandDTO("ERROR Brand");
    }

    public void createException() {
        throw new IllegalArgumentException("Prueba Resilience y Circuit Breaker");
    }
}
