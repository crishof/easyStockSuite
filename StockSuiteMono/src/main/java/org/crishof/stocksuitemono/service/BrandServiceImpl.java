package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Brand;
import org.crishof.stocksuitemono.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepository brandRepository;

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand findById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }


    @Override
    public void save(Brand brand) {
        brandRepository.save(brand);
    }

    @Override
    public Brand update(Long id, Brand brand) {

        Brand brand1 = this.findById(id);

        brand1.setName(brand.getName());

        return brandRepository.save(brand1);
    }

    @Override
    public void deleteById(Long id) {
        brandRepository.deleteById(id);
    }

    @Override
    public Brand getBrandByName(String name) {
        return brandRepository.findByNameIgnoreCase(name).orElse(null);
    }

    @Override
    public Brand saveByName(String brandName) {
        if (brandRepository.findByNameIgnoreCase(brandName).isEmpty()) {
            Brand brand = new Brand();
            brand.setName(brandName);
            return brandRepository.save(brand);
        } else {
            return this.getBrandByName(brandName);
        }
    }
}

