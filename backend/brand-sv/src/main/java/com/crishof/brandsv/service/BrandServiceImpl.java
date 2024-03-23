package com.crishof.brandsv.service;

import com.crishof.brandsv.apiCient.ImageAPIClient;
import com.crishof.brandsv.exeption.BrandNotFoundExeption;
import com.crishof.brandsv.exeption.DuplicateNameException;
import com.crishof.brandsv.model.Brand;
import com.crishof.brandsv.repository.BrandRepository;
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
    ImageAPIClient imageAPIClient;

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getById(UUID id) {
        return brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundExeption(id));
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
    public Brand update(UUID id, String name /*, MultipartFile logo */) {

        Brand brand = this.getById(id);
        if (brand != null) {
            if (Objects.equals(name, "")) {
                throw new IllegalArgumentException("Brand name cannot be empty");
            }
            brand.setName(name);
//            Image image = imageService.save(logo);
//            brand.setLogo(image);
            return brandRepository.save(brand);
        } else {
            throw new BrandNotFoundExeption(id);
        }
    }

    @Override
    public Brand updateImage(UUID uuid, String imageUrl) {

        Brand brand = brandRepository.findById(uuid)
                .orElseThrow(() -> new BrandNotFoundExeption("Brand not found with id: " + uuid));

        if (brand != null) {

            if (brand.getImageUrl() != null) {
                imageAPIClient.deleteImageByUrl(brand.getImageUrl(), Brand.class.getSimpleName());
            }
            brand.setImageUrl(imageUrl);
        } else {
            throw new BrandNotFoundExeption(uuid);
        }
        return brandRepository.save(brand);
    }

    @Override
    public void deleteById(UUID id) {

        Brand brand = brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundExeption(id));
//        List<Product> products = productRepository.findAllByBrandName(brand.getName());
//        if (!products.isEmpty()) {
//            throw new IllegalStateException("Cannot delete brand with associated products");
//        }
        brandRepository.deleteById(id);

        if (brand.getImageUrl() != null) {
//            imageService.deleteById(brand.getLogo().getId());
        }
    }

    @Override
    public Brand getByName(String name) {

        return brandRepository.findByNameIgnoreCase(name).orElseThrow(() -> new BrandNotFoundExeption("Brand with name " + name + " not found"));
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

