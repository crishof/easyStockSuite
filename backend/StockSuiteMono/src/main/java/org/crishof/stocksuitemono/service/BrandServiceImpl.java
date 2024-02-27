package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.exception.duplicated.DuplicateNameException;
import org.crishof.stocksuitemono.exception.notFound.BrandNotFoundException;
import org.crishof.stocksuitemono.model.Brand;
import org.crishof.stocksuitemono.model.Image;
import org.crishof.stocksuitemono.model.Product;
import org.crishof.stocksuitemono.repository.BrandRepository;
import org.crishof.stocksuitemono.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ImageService imageService;

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

        if (Objects.equals(brand.getName(), "")) {
            throw new IllegalArgumentException("Brand name cannot be empty");
        }
        if (brandRepository.findByNameIgnoreCase(brand.getName()).isPresent()) {
            throw new DuplicateNameException("Brand with name " + brand.getName() + " already exist");
        }
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(UUID id, String name, MultipartFile logo) {

        Brand brand = this.getById(id);
        if (brand != null) {
            if (Objects.equals(name, "")) {
                throw new IllegalArgumentException("Brand name cannot be empty");
            }
            brand.setName(name);
            Image image = imageService.save(logo);
            brand.setLogo(image);
            return brandRepository.save(brand);
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
    public Brand getByName(String name) {

        return brandRepository.findByNameIgnoreCase(name).orElseThrow(() -> new BrandNotFoundException("Brand with name " + name + " not found"));
    }

    @Override
    public Brand saveByName(String brandName) {

        return brandRepository.findByNameIgnoreCase(brandName).orElseGet(() -> {
            Brand brand = new Brand();
            brand.setName(brandName.toUpperCase());
            return brandRepository.save(brand);
        });
    }
}

