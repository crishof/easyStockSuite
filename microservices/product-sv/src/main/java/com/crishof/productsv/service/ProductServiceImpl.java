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
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    final
    ProductRepository productRepository;
    final
    CategoryAPIClient categoryAPIClient;
    final
    PriceApiClient priceApiClient;
    final
    BrandAPIClient brandAPIClient;
    final
    SupplierAPIClient supplierAPIClient;

    public ProductServiceImpl(ProductRepository productRepository, CategoryAPIClient categoryAPIClient, PriceApiClient priceApiClient, BrandAPIClient brandAPIClient, SupplierAPIClient supplierAPIClient) {
        this.productRepository = productRepository;
        this.categoryAPIClient = categoryAPIClient;
        this.priceApiClient = priceApiClient;
        this.brandAPIClient = brandAPIClient;
        this.supplierAPIClient = supplierAPIClient;
    }

    @Override
    public ProductResponse save(ProductRequest productRequest) {


        Product product = new Product();

        product.setBrandId(this.getIdByName(productRequest.getBrandName(), "brand"));
        product.setCode(productRequest.getCode());
        product.setModel(productRequest.getModel());
        product.setDescription(productRequest.getDescription());
        if (productRequest.getCategoryName() != null) {
            product.setCategoryId(this.getIdByName(productRequest.getCategoryName(), "category"));
        }
        product.setSupplierId(productRequest.getSupplierId());
        product.setSupplierProductId(productRequest.getSupplierProductId());
        product.setHidden(false);

        ResponseEntity<?> response = priceApiClient.save(new PriceRequest(
                productRequest.getPurchasePrice(),
                productRequest.getSellingPrice(),
                productRequest.getWebSellingPrice(),
                productRequest.getTaxRate()
        ));
        String idString = (String) response.getBody();
        assert idString != null;
        UUID priceId = UUID.fromString(idString);
        product.setPriceId(priceId);

        return this.toProductResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse update(UUID id, ProductRequest productRequest) {

        return null;
    }

    @Override
    public void deleteById(UUID id) {

        productRepository.deleteById(id);

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
    public List<ProductResponse> getAllByFilter(String filter) {
        List<Product> products = new ArrayList<>();

        if (!filter.isEmpty()) {
            System.out.println("filter = " + filter);
            ResponseEntity<?> brandResponse = brandAPIClient.getAllIdByFilter(filter);
            System.out.println("brandResponse = " + brandResponse);
            if (brandResponse.getStatusCode() == HttpStatus.OK) {

                List<String> brandIdList = (List<String>) brandResponse.getBody();

                System.out.println("brandIdList = " + brandIdList);

                if (brandIdList != null && !brandIdList.isEmpty()) {
                    List<UUID> brandIds = brandIdList.stream().map(UUID::fromString).collect(Collectors.toList());
                    System.out.println("brandIds = " + brandIds);
                    for (UUID id : brandIds) {
                        products.addAll(productRepository.findAllByBrandId(id));
                    }
                }
            }
        }
        products.addAll(productRepository.findAllByModelContainingIgnoreCase(filter));
        products.addAll(productRepository.findAllByDescriptionContainingIgnoreCase(filter));

        return products.stream().map(this::toProductResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getAllByFilterAndStock(String filter) {
        return List.of();
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


    @Override
    public List<ProductResponse> getAll() {

        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
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

    public UUID getIdByName(String name, String entityName) {
        // Manejar caso de entidad desconocida
        return switch (entityName) {
            case "brand" -> {
                ResponseEntity<?> response = brandAPIClient.getIdByName(name);
                yield handleResponse(response, name, "brand");
            }
            case "category" -> {
                ResponseEntity<?> response = categoryAPIClient.getIdByName(name);
                yield handleResponse(response, name, "category");
            }
            case "supplier" -> {
                ResponseEntity<?> response = supplierAPIClient.getIdByName(name);
                yield handleResponse(response, name, "supplier");
            }
            default -> throw new IllegalArgumentException("Invalid entity name: " + entityName);
        };
    }

    private UUID handleResponse(ResponseEntity<?> response, String name, String entityName) {
        if (response == null || response.getStatusCode() != HttpStatus.OK) {
            // Manejar caso de respuesta nula o no exitosa
            throw new EntityNotFoundException("Error retrieving " + entityName + " ID for " + entityName + ": " + name);
        }

        String idString = (String) response.getBody();
        if (idString == null) {
            // Manejar caso de respuesta con cuerpo nulo
            throw new EntityNotFoundException("No " + entityName + " ID found for " + entityName + ": " + name);
        }

        return UUID.fromString(idString);
    }


}

