package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.exception.duplicated.DuplicateNameException;
import org.crishof.stocksuitemono.exception.notFound.BrandNotFoundException;
import org.crishof.stocksuitemono.model.Brand;
import org.crishof.stocksuitemono.model.Product;
import org.crishof.stocksuitemono.repository.BrandRepository;
import org.crishof.stocksuitemono.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getById(UUID id) {
        return brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundException(id));
    }


    @Override
    public Brand save(Brand brand) {

        if (brandRepository.findByNameIgnoreCase(brand.getName()).isPresent()) {
            throw new DuplicateNameException("Brand with name " + brand.getName() + " already exist");
        }
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(UUID id, Brand brand) {

        Brand brand1 = this.getById(id);
        if (brand1 != null) {
            brand1.setName(brand.getName());
            return brandRepository.save(brand1);
        } else {
            throw new BrandNotFoundException(id);
        }
    }

    @Override
    public void deleteById(UUID id) {

        Brand brand = brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundException(id));
        List<Product> products = productRepository.findAllByBrandName(brand.getName());
        if (!products.isEmpty()) {
            throw new IllegalStateException("Cannot delete brand with asociated products");
        }
        brandRepository.deleteById(id);
    }

    @Override
    public Brand getBrandByName(String name) {
        return brandRepository.findByNameIgnoreCase(name).orElse(null);
    }

    @Override
    public Brand saveByName(String brandName) {

        return brandRepository.findByNameIgnoreCase(brandName).orElseGet(() -> {
            Brand brand = new Brand();
            brand.setName(brandName);
            return brandRepository.save(brand);
        });
    }
}

