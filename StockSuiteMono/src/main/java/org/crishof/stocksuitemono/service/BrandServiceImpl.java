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
    public Brand findtById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }


    @Override
    public void save(Brand brand) {
        brandRepository.save(brand);
    }

    @Override
    public Brand update(Long id, Brand brand) {

        Brand brand1 = this.findtById(id);

        brand1.setName(brand.getName());

        return brandRepository.save(brand1);
    }

    @Override
    public void deleteById(Long id) {
        brandRepository.deleteById(id);
    }
}

