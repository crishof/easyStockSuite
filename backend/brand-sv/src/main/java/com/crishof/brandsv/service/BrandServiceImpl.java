package com.crishof.brandsv.service;

import com.crishof.brandsv.apiCient.ImageAPIClient;
import com.crishof.brandsv.dto.BrandRequest;
import com.crishof.brandsv.dto.BrandResponse;
import com.crishof.brandsv.exeption.BrandNotFoundException;
import com.crishof.brandsv.exeption.DuplicateNameException;
import com.crishof.brandsv.model.Brand;
import com.crishof.brandsv.repository.BrandRepository;
import com.crishof.brandsv.utils.BrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ImageAPIClient imageAPIClient;

    @Override
    public List<BrandResponse> getAll() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(BrandMapper::toBrandResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BrandResponse getById(UUID id) {

        Brand brand = brandRepository.findById(id).orElseThrow(
                () -> new BrandNotFoundException(id));
        return BrandMapper.toBrandResponse(brand);
    }

    @Override
    public BrandResponse getByName(String name) {

        Brand brand = brandRepository.findByNameIgnoreCase(name).orElseThrow(
                () -> new BrandNotFoundException("Brand not found with name: " + name));
        return BrandMapper.toBrandResponse(brand);
    }


    @Override
    public BrandResponse save(BrandRequest brandRequest) {

        Brand brand = new Brand(brandRequest);

        if (brandRepository.findByNameIgnoreCase(brandRequest.getName()).isPresent()) {
            throw new DuplicateNameException("Brand with name " + brandRequest.getName() + " already exist");
        }
        if (Objects.equals(brand.getName(), "")) {
            throw new IllegalArgumentException("Brand name cannot be empty");
        }
        return new BrandResponse(brandRepository.save(brand));

    }

    @Override
    public BrandResponse updateBrandName(UUID id, String name) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new BrandNotFoundException(id));

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Brand name cannot be empty");
        }
        brand.setName(name);
        return new BrandResponse(brandRepository.save(brand));
    }

    @Override
    public BrandResponse updateBrand(UUID uuid, String brandName, String imageUrl) {

        Brand brand = brandRepository.findById(uuid)
                .orElseThrow(() -> new BrandNotFoundException(uuid));

        if (brandName == null || brandName.isEmpty()) {
            throw new IllegalArgumentException("Brand name cannot be empty");
        }
        brand.setName(brandName);
        brand.setImageUrl(imageUrl);

        return new BrandResponse(brandRepository.save(brand));
    }

    @Override
    public BrandResponse updateImage(UUID uuid, String imageUrl) {

        Brand brand = brandRepository.findById(uuid)
                .orElseThrow(() -> new BrandNotFoundException(uuid));

        if (brand.getImageUrl() != null) {
            imageAPIClient.deleteImageByUrl(brand.getImageUrl(), Brand.class.getSimpleName());
        }
        brand.setImageUrl(imageUrl);


        return new BrandResponse(brandRepository.save(brand));
    }

    @Override
    public void deleteById(UUID id) {

        Brand brand = brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundException(id));

        if (brand.getImageUrl() != null) {
            imageAPIClient.deleteImageByUrl(brand.getImageUrl(), Brand.class.getSimpleName());
        }

        brandRepository.deleteById(id);

    }
}

