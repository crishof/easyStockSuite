package com.crishof.brandsv.service;

import com.crishof.brandsv.dto.BrandRequest;
import com.crishof.brandsv.dto.BrandResponse;

import java.util.List;
import java.util.UUID;

public interface BrandService {

    List<BrandResponse> getAll();

    BrandResponse getById(UUID id);

    BrandResponse getByName(String name);

    BrandResponse getByNameOrCreateNew(String name);

    BrandResponse getByFilter(String filter);

    List<BrandResponse> getAllByFilter(String filter);

    BrandResponse save(BrandRequest brandRequest);

    BrandResponse updateBrandName(UUID id, String name);

    BrandResponse updateBrand(UUID uuid, String brandName, String imageUrl);

    BrandResponse updateImage(UUID uuid, String imageUrl);

    void deleteById(UUID id);

}
