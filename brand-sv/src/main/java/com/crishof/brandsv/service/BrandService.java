package com.crishof.brandsv.service;

import com.crishof.brandsv.model.Brand;

import java.util.List;

public interface BrandService {

    void save(Brand brand);

    List<Brand> findAll();

    Brand findById(Long id);

    void update(Long id, Brand brand);

    void deleteById(Long id);

    Long getBrandIdByName(String brand);

    Brand getBrandByName(String name);

    void saveBrandName(String brand);
}
