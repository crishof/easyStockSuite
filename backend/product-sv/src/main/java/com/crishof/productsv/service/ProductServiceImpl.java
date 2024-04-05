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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    final ProductRepository productRepository;
    final BrandAPIClient brandAPIClient;
    final CategoryAPIClient categoryAPIClient;
    final SupplierAPIClient supplierAPIClient;
    final PriceApiClient priceApiClient;

    public ProductServiceImpl(ProductRepository productRepository, BrandAPIClient brandAPIClient, CategoryAPIClient categoryAPIClient, SupplierAPIClient supplierAPIClient, PriceApiClient priceApiClient) {
        this.productRepository = productRepository;
        this.brandAPIClient = brandAPIClient;
        this.categoryAPIClient = categoryAPIClient;
        this.supplierAPIClient = supplierAPIClient;
        this.priceApiClient = priceApiClient;
    }

    @Override
    public List<ProductResponse> getAll() {
        List<Product> products = this.productRepository.findAll();
        return products.stream().map(this::toProductResponse).collect(Collectors.toList());
    }

    @Override
    public ProductResponse getById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return this.toProductResponse(product);
    }

    public ProductResponse toProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();

        // Mapea los atributos comunes entre Product y ProductResponse
        productResponse.setId(product.getId());
        productResponse.setCode(product.getCode());
        productResponse.setModel(product.getModel());
        productResponse.setDescription(product.getDescription());
        productResponse.setSupplierId(product.getSupplierId());
        productResponse.setHidden(product.isHidden());
        productResponse.setImageId(product.getImageId());

        String brandName = this.getBrandName(product.getBrandId());
        productResponse.setBrandName(brandName);

        if (product.getCategoryId() != null) {
            String categoryName = this.getCategoryName(product.getCategoryId());
            productResponse.setCategoryName(categoryName);
        }


        productResponse.setPriceResponse(priceApiClient.getById(product.getPriceId()));

        productResponse.setStockIds(product.getStockIds());

        productResponse.setDimension(product.getDimensionId());


        return productResponse;
    }

    public String getCategoryName(UUID uuid) {
        ResponseEntity<String> response = categoryAPIClient.getNameById(uuid);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            return "Not available";
        }
    }

    public String getBrandName(UUID uuid) {
        try {
            ResponseEntity<String> response = brandAPIClient.getNameById(uuid);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                // Si la llamada no es exitosa, devuelve un valor predeterminado
                return "Not available";
            }
        } catch (Exception ex) {
            // Manejar la excepción aquí
            return "Error fetching brand name";
        }
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

        PriceRequest priceRequest = new PriceRequest(productRequest.getPurchasePrice(), productRequest.getSellingPrice(), productRequest.getTaxRate());
        ResponseEntity<?> priceResponse = priceApiClient.save(priceRequest);
        UUID priceId;

        if (priceResponse.getStatusCode() == HttpStatus.OK) {
            String priceString = (String) priceResponse.getBody();
            assert priceString != null;
            priceId = UUID.fromString(priceString);

            product.setPriceId(priceId);
        }

        Product saved = productRepository.save(product);
        return this.toProductResponse(saved);
    }

    @Override
    public ProductResponse update(UUID id, ProductRequest productRequest) {

        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        product.setModel(productRequest.getModel());
        product.setDescription(productRequest.getDescription());
        return this.toProductResponse(product);
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

        return products.stream().map(this::toProductResponse).collect(Collectors.toList());
    }

    public boolean checkProductsByBrand(UUID brandId) {
        long productCount = productRepository.countByBrandId(brandId);
        return productCount > 0;
    }

    public Long countProductsByBrand(UUID brandId) {
        return productRepository.countByBrandId(brandId);
    }

    public void removeCategory(UUID categoryId) {
        List<Product> products = productRepository.findAllByCategoryId(categoryId);

        for (Product product : products) {
            product.setCategoryId(null);
            productRepository.save(product);
        }
    }


// TODO corregir stocks
//    @Override
//    public List<ProductResponse> getAllByFilterAndStock(String filter) {
//
//        return this.getAllByFilter(filter).stream().filter(productResponse -> productResponse.getStockIds() > 0).collect(Collectors.toList());
//    }
}
