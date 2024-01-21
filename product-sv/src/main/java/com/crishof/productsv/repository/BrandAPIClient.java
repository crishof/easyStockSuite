package com.crishof.productsv.repository;

import com.crishof.productsv.dto.BrandDTO;
import com.crishof.productsv.model.Brand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "brandapi", url = "http://localhost:9002/brand")
@FeignClient(name = "brand-sv")
public interface BrandAPIClient {

    @GetMapping("/brand/getById/{brandId}")
    BrandDTO getBrandInfo(@PathVariable("brandId") Long id);

    @PostMapping("/brand/save")
    String save(Brand brand);

    @GetMapping("/brand/findByName/{brand}")
    Long getBrandIdByName(@PathVariable("brand") String brand);

    @PostMapping("/brand/saveBrandName")
    void saveBrandName(@RequestBody String brand);
}
