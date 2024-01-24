package com.crishof.orchestratorsv.service;

import com.crishof.orchestratorsv.dto.BrandDTO;

public interface ProductService {

    BrandDTO getBrandInfo(Long brandId);
}
