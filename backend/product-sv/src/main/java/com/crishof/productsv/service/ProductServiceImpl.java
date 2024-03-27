package com.crishof.productsv.service;

import com.crishof.productsv.apiCient.BrandAPIClient;
import com.crishof.productsv.apiCient.CategoryAPIClient;
import com.crishof.productsv.apiCient.PriceApiClient;
import com.crishof.productsv.apiCient.SupplierAPIClient;
import com.crishof.productsv.dto.PriceRequest;
import com.crishof.productsv.dto.ProductRequest;
import com.crishof.productsv.dto.ProductResponse;
import com.crishof.productsv.exeption.ProductNotFoundException;
import com.crishof.productsv.model.Product;
import com.crishof.productsv.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandAPIClient brandAPIClient;
    @Autowired
    CategoryAPIClient categoryAPIClient;
    @Autowired
    SupplierAPIClient supplierAPIClient;

    @Autowired
    PriceApiClient priceApiClient;

    @Override
    public List<ProductResponse> getAll() {
        List<Product> products = this.productRepository.findAll();
        return products.stream().map(ProductResponse::new).toList();
    }

    @Override
    public ProductResponse getById(UUID id) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return new ProductResponse(product);
    }

    @Override
    public ProductResponse save(ProductRequest productRequest) {

        Product product = new Product(productRequest);

        //TODO set brand

        ResponseEntity<?> brandResponse = brandAPIClient.getIdByName(productRequest.getBrandName());
        UUID brandId;
        if (brandResponse.getStatusCode() == HttpStatus.OK) {
            String brandString = (String) brandResponse.getBody();
            assert brandString != null;
            brandId = UUID.fromString(brandString);
            product.setBrandId(brandId);
        }

        //TODO set category

        if (productRequest.getCategoryName() != null) {
            ResponseEntity<?> categoryResponse = categoryAPIClient.getIdByName(productRequest.getCategoryName());
            UUID categoryId;
            if (categoryResponse.getStatusCode() == HttpStatus.OK) {
                String categoryString = (String) categoryResponse.getBody();
                assert categoryString != null;
                categoryId = UUID.fromString(categoryString);
                product.setCategoryId(categoryId);
            }
        }

        //TODO set Supplier

        ResponseEntity<?> supplierResponse = supplierAPIClient.getIdByName(productRequest.getSupplierName());
        UUID supplierId;
        if (supplierResponse.getStatusCode() == HttpStatus.OK) {
            String supplierString = (String) supplierResponse.getBody();
            assert supplierString != null;
            supplierId = UUID.fromString(supplierString);
            product.setSupplierId(supplierId);
        }

        //TODO set prices

        PriceRequest priceRequest = new PriceRequest(
                productRequest.getPurchasePrice(),
                productRequest.getSellingPrice(),
                productRequest.getTaxRate());
        ResponseEntity<?> priceResponse = priceApiClient.save(priceRequest);
        UUID priceId;

        if (priceResponse.getStatusCode() == HttpStatus.OK) {
            String priceString = (String) priceResponse.getBody();
            assert priceString != null;
            priceId = UUID.fromString(priceString);

            product.setPriceId(priceId);
        }

        Product saved = productRepository.save(product);
        return new ProductResponse(saved);
    }

    @Override
    public ProductResponse update(UUID id, ProductRequest productRequest) {

        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        product.setModel(productRequest.getModel());
        product.setDescription(productRequest.getDescription());
        return new ProductResponse(productRepository.save(product));
    }

    @Override
    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponse> getAllByFilter(String filter) {
        List<Product> products = new ArrayList<>();

        ResponseEntity<?> brandResponse = brandAPIClient.getIdByName(filter);
        UUID brandId;
        if (brandResponse.getStatusCode() == HttpStatus.OK) {
            String brandString = (String) brandResponse.getBody();
            assert brandString != null;
            brandId = UUID.fromString(brandString);
            products.addAll(productRepository.findAllByBrandId(brandId));
        }
        products.addAll(productRepository.findAllByModelContainingIgnoreCase(filter));
        products.addAll(productRepository.findAllByDescriptionContainingIgnoreCase(filter));

        return products.stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getAllByFilterAndStock(String filter) {

        return this.getAllByFilter(filter).stream().filter(productResponse -> productResponse.getStock() > 0).collect(Collectors.toList());
    }
}
