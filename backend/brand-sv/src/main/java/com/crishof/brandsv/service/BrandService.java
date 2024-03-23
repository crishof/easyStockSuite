package com.crishof.brandsv.service;

import com.crishof.brandsv.model.Brand;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface BrandService {

    List<Brand> getAll();

    Brand getById(UUID id);

    Brand save(Brand brand);

    Brand update(UUID id, String name /* , MultipartFile logo */);

    Brand updateLogo(UUID id, MultipartFile logo);

    void deleteById(UUID id);

    Brand getByName(String name);

    Brand saveByName(String brandName);

}
