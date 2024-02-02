package com.crishof.orchestratorsv.service;

import com.crishof.orchestratorsv.dto.BrandDTO;
import com.crishof.orchestratorsv.dto.ProductDTO;
import com.crishof.orchestratorsv.model.Product;
import com.crishof.orchestratorsv.repository.BrandAPIClient;
import com.crishof.orchestratorsv.repository.CategoryAPIClient;
import com.crishof.orchestratorsv.repository.ProductAPIClient;
import com.crishof.orchestratorsv.repository.SupplierAPIClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    BrandAPIClient brandAPIClient;
    @Autowired
    ProductAPIClient productAPIClient;
    @Autowired
    CategoryAPIClient categoryAPIClient;
    @Autowired
    SupplierAPIClient supplierAPIClient;

    @Override
    @CircuitBreaker(name = "brand-sv", fallbackMethod = "fallbackGetBrandInfo")
    @Retry(name = "brand-sv")
    public BrandDTO getBrandInfo(Long brandId) {
        BrandDTO brandDTO = brandAPIClient.getBrandInfo(brandId);

//        createException();

        return brandDTO;
    }

    @Override
    public void saveProduct(ProductDTO productDTO) {


        Product product = new Product();
        product.setCode(productDTO.getCode());
        product.setModel(productDTO.getModel());
        product.setDescription(productDTO.getDescription());
        product.setBrandId(brandAPIClient.getBrandIdByName(productDTO.getBrand()));
        product.setCategoryId(categoryAPIClient.getCategoryIdByName(productDTO.getCategory()));
        product.setSupplierId(supplierAPIClient.getSupplierIdByName(productDTO.getSupplier()));

        productAPIClient.save(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {

        List<Product> products = productAPIClient.getAllProducts();
        List<ProductDTO> dtoList;
        for(Product p : products){
        ProductDTO productDTO = new ProductDTO();

        productDTO.setBrand(productDTO.getBrand());
        productDTO.setCategory(p.getCategoryId());
        }
        return

    }

    public BrandDTO fallbackGetBrandInfo(Throwable throwable) {
        return new BrandDTO("ERROR Brand");
    }

    public void createException() {
        throw new IllegalArgumentException("Prueba Resilience y Circuit Breaker");
    }
}
