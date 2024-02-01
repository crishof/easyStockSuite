package com.crishof.orchestratorsv.service;

import com.crishof.orchestratorsv.dto.BrandDTO;
import com.crishof.orchestratorsv.dto.ProductDTO;
import com.crishof.orchestratorsv.repository.BrandAPIClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ProductService {

    BrandDTO getBrandInfo(Long brandId);

    void saveProduct(ProductDTO product);

    List<ProductDTO> getAllProducts();
}
