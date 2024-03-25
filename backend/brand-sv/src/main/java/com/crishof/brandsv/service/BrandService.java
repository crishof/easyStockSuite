package com.crishof.brandsv.service;

import com.crishof.brandsv.dto.BrandRequest;
import com.crishof.brandsv.dto.BrandResponse;
import com.crishof.brandsv.model.Brand;

import java.util.List;
import java.util.UUID;

public interface BrandService {

    List<BrandResponse> getAll();

    BrandResponse getById(UUID id);

    Brand getByName(String name);

    BrandResponse save(BrandRequest brandRequest);

    BrandResponse updateBrandName(UUID id, String name /* , MultipartFile logo */);

    BrandResponse updateBrand(UUID uuid, String brandName, String imageUrl);

    Brand updateImage(UUID uuid, String imageUrl);

    void deleteById(UUID id);

}
