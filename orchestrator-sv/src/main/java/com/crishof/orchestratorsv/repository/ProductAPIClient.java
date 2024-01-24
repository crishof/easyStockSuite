package com.crishof.orchestratorsv.repository;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-sv")
public interface ProductAPIClient {


}
