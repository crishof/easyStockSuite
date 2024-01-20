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
}
