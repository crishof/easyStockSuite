package com.crishof.brandsv.service;

import com.crishof.brandsv.model.Brand;
import com.crishof.brandsv.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepository brandRepository;

    @Override
    public void save(Brand brand) {
        brandRepository.save(brand);
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand findById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Long id, Brand brand) {

        Brand brand1 = this.findById(id);
        brand1.setName(brand.getName());

        brandRepository.save(brand1);

    }

    @Override
    public void deleteById(Long id) {

        brandRepository.deleteById(id);
    }

    @Override
    public Long getBrandIdByName(String brand) {
        return brandRepository.findByNameIgnoreCase(brand).getId();
    }

    @Override
    public Brand getBrandByName(String name) {
        return brandRepository.findByNameIgnoreCase(name);
    }

    @Override
    public void saveBrandName(String brandName) {

        Brand brand = new Brand();
        Brand brand1 = brandRepository.findByNameIgnoreCase(brandName);

        if (brand1 == null) {
            brand.setName(brandName);
            this.save(brand);
        }
    }
}
