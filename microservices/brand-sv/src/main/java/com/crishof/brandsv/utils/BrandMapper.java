package com.crishof.brandsv.utils;

import com.crishof.brandsv.dto.BrandResponse;
import com.crishof.brandsv.model.Brand;
import org.modelmapper.ModelMapper;

public class BrandMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static BrandResponse toBrandResponse(Brand brand) {

        return modelMapper.map(brand, BrandResponse.class);
    }
}