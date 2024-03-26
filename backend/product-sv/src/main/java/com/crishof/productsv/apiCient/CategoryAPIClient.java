package com.crishof.productsv.apiCient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "category-sv", url = "http://localhost:9003")
public interface CategoryAPIClient {
}
