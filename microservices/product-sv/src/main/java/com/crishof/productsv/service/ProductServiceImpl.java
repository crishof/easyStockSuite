package com.crishof.productsv.service;

import com.crishof.productsv.apiclient.*;
import com.crishof.productsv.dto.*;
import com.crishof.productsv.exeption.ProductNotFoundException;
import com.crishof.productsv.model.Product;
import com.crishof.productsv.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final String BRAND_ENTITY = "Brand";
    private static final String CATEGORY_ENTITY = "Category";
    private static final String SUPPLIER_ENTITY = "Supplier";
    private final ProductRepository productRepository;
    private final CategoryAPIClient categoryAPIClient;
    private final PriceApiClient priceApiClient;
    private final BrandAPIClient brandAPIClient;
    private final SupplierAPIClient supplierAPIClient;
    private final StockAPIClient stockAPIClient;

    @Override
    public ProductResponse save(ProductRequest productRequest) {
        Product product = new Product();

        product.setBrandId(this.getIdByName(productRequest.getBrandName(), BRAND_ENTITY));
        product.setCode(productRequest.getCode());
        product.setModel(productRequest.getModel());
        product.setDescription(productRequest.getDescription());
        if (productRequest.getCategoryName() != null) {
            product.setCategoryId(this.getIdByName(productRequest.getCategoryName(), CATEGORY_ENTITY));
        }
        product.setSupplierId(productRequest.getSupplierId());
        product.setSupplierProductId(productRequest.getSupplierProductId());
        product.setHidden(false);

        product.setStockIds(new ArrayList<>());

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
    public String updateFromInvoice(InvoiceUpdateRequest invoiceUpdateRequest) {

        List<SupplierInvoiceItem> invoiceItems = invoiceUpdateRequest.getInvoiceItems();

        for (SupplierInvoiceItem invoiceItem : invoiceItems) {

            Product product = productRepository.findById(invoiceItem.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(invoiceItem.getProductId()));

            StockRequest stockRequest = new StockRequest();
            stockRequest.setProductId(invoiceItem.getProductId());
            stockRequest.setQuantity(invoiceItem.getQuantity());
            stockRequest.setBranchId(invoiceUpdateRequest.getBranchId());
            stockRequest.setLocationId(invoiceUpdateRequest.getLocationId());

            Set<UUID> stocks = new HashSet<>(product.getStockIds());

            UUID stockId = stockAPIClient.save(stockRequest);
            boolean isAdded = stocks.add(stockId);

            if (isAdded) {
                product.setStockIds(new ArrayList<>(stocks));
            }

            PriceRequest priceRequest = new PriceRequest();
            priceRequest.setPurchasePrice(invoiceItem.getPrice());
            priceRequest.setTaxRate(invoiceItem.getTaxRate());
            priceRequest.setDiscountRate(invoiceItem.getDiscountRate());

            priceApiClient.update(product.getPriceId(), priceRequest);

            productRepository.save(product);
        }

        return "Products updated successfully";
    }

    @Override
    public String updateFromCustomerInvoice(InvoiceUpdateRequest invoiceUpdateRequest) {

        List<SupplierInvoiceItem> invoiceItems = invoiceUpdateRequest.getInvoiceItems();

        for (SupplierInvoiceItem invoiceItem : invoiceItems) {

            Product product = productRepository.findById(invoiceItem.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(invoiceItem.getProductId()));

            Optional<StockResponse> stockResponse = stockAPIClient.getAllProductStocks(product.getStockIds()).stream()
                    .filter(s -> s.getBranchId().equals(invoiceUpdateRequest.getBranchId()) && s.getLocationId().equals(invoiceUpdateRequest.getLocationId()))
                    .findFirst();

            if (stockResponse.isPresent()) {

                StockRequest stockRequest = new StockRequest();
                stockRequest.setProductId(invoiceItem.getProductId());
                stockRequest.setQuantity(invoiceItem.getQuantity() * -1);
                stockAPIClient.updateQuantity(stockResponse.get().getId(), stockRequest);
            }
            productRepository.save(product);
        }
        return "Products updated successfully";
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
                return "Not available";
            }
        } catch (Exception ex) {
            return "Error fetching brand name";
        }
    }

    @Override
    public List<ProductResponse> getAllByFilter(String filter) {
        List<Product> products = new ArrayList<>();

        if (!filter.isEmpty()) {

            ResponseEntity<?> brandResponse = brandAPIClient.getAllIdByFilter(filter);

            if (brandResponse.getStatusCode() == HttpStatus.OK) {

                List<String> brandIdList = (List<String>) brandResponse.getBody();


                if (brandIdList != null && !brandIdList.isEmpty()) {
                    List<UUID> brandIds = brandIdList.stream().map(UUID::fromString).toList();

                    for (UUID id : brandIds) {
                        products.addAll(productRepository.findAllByBrandId(id));
                    }
                }
            }
        }
        products.addAll(productRepository.findAllByModelContainingIgnoreCase(filter));
        products.addAll(productRepository.findAllByDescriptionContainingIgnoreCase(filter));

        return products.stream().map(this::toProductResponse).distinct().toList();
    }

    @Override
    public List<ProductResponse> getAllByFilterAndSupplier(String filter, UUID supplierId) {
        return this.getAllByFilter(filter).stream().filter(product -> product.getSupplierId().equals(supplierId)).toList();
    }

    @Override
    public List<ProductResponse> getAllByFilterAndStock(String filter) {
        List<Product> productsWithStock = new ArrayList<>();

        if (!filter.isEmpty()) {
            ResponseEntity<?> brandResponse = brandAPIClient.getAllIdByFilter(filter);

            if (brandResponse.getStatusCode() == HttpStatus.OK) {
                List<String> brandIdList = (List<String>) brandResponse.getBody();

                if (brandIdList != null && !brandIdList.isEmpty()) {
                    List<UUID> brandIds = brandIdList.stream().map(UUID::fromString).toList();

                    for (UUID id : brandIds) {
                        productsWithStock.addAll(productRepository.findAllByBrandId(id));
                    }
                }
            }
        }

        // Add products from model and description search to the list
        List<Product> additionalProducts = new ArrayList<>();
        additionalProducts.addAll(productRepository.findAllByModelContainingIgnoreCase(filter));
        additionalProducts.addAll(productRepository.findAllByDescriptionContainingIgnoreCase(filter));

        // Check stock for all products and filter
        for (Product product : additionalProducts) {
            if (!productsWithStock.contains(product)) {  // Check if not already included
                productsWithStock.add(product);
            }
        }

        // Filter products to include only those with stock > 0
        return productsWithStock.stream()
                .filter(product -> {
                    Integer stock = (Integer) stockAPIClient.getTotalStockForProduct(product.getStockIds()).getBody();
                    return stock != null && stock > 0;
                })
                .map(this::toProductResponse)
                .distinct()
                .toList();
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
                .toList();
    }

    @Override
    public ProductResponse getById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return this.toProductResponse(product);
    }

    public ProductResponse toProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();

        productResponse.setId(product.getId());
        productResponse.setCode(product.getCode());
        productResponse.setModel(product.getModel());
        productResponse.setDescription(product.getDescription());
        productResponse.setSupplierId(product.getSupplierId());
        productResponse.setHidden(product.isHidden());
        productResponse.setImageId(product.getImageId());
        productResponse.setSupplierProductId(product.getSupplierProductId());

        String brandName = this.getBrandName(product.getBrandId());
        productResponse.setBrandName(brandName);

        if (product.getCategoryId() != null) {
            String categoryName = this.getCategoryName(product.getCategoryId());
            productResponse.setCategoryName(categoryName);
        }


        productResponse.setPriceResponse(priceApiClient.getById(product.getPriceId()));

        productResponse.setStockResponses(stockAPIClient.getAllProductStocks(product.getStockIds()));

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
            case BRAND_ENTITY -> {
                ResponseEntity<?> response = brandAPIClient.getIdByName(name);
                yield handleResponse(response, name, BRAND_ENTITY);
            }
            case CATEGORY_ENTITY -> {
                ResponseEntity<?> response = categoryAPIClient.getIdByName(name);
                yield handleResponse(response, name, CATEGORY_ENTITY);
            }
            case SUPPLIER_ENTITY -> {
                ResponseEntity<?> response = supplierAPIClient.getIdByName(name);
                yield handleResponse(response, name, SUPPLIER_ENTITY);
            }
            default -> throw new IllegalArgumentException("Invalid entity name: " + entityName);
        };
    }

    private UUID handleResponse(ResponseEntity<?> response, String name, String entityName) {
        if (response == null || response.getStatusCode() != HttpStatus.OK) {
            throw new EntityNotFoundException("Error retrieving " + entityName + " ID for " + entityName + ": " + name);
        }

        String idString = (String) response.getBody();
        if (idString == null) {
            throw new EntityNotFoundException("No " + entityName + " ID found for " + entityName + ": " + name);
        }

        return UUID.fromString(idString);
    }

    @Override
    public String importSupplierProducts(List<SupplierProductRequest> productList) {
        int importedCount = 0;
        int alreadyImportedCount = 0;

        for (SupplierProductRequest request : productList) {

            UUID brandId = this.getIdByName(request.getBrand(), BRAND_ENTITY);

            if (productRepository.findByBrandIdAndAndModelAndDescriptionAndSupplierId(
                    brandId, request.getModel(), request.getDescription(), request.getSupplierId()) == null) {

                ProductRequest productRequest = new ProductRequest(request);
                this.save(productRequest);
                importedCount++;
            } else {
                alreadyImportedCount++;
            }
        }

        String message = "Task completed successfully: ";
        if (productList.isEmpty()) {
            message = "Product list is empty";
        }
        if (importedCount > 0) {
            message += " " + importedCount + " products imported";
        }
        if (alreadyImportedCount > 0) {
            message += " " + alreadyImportedCount + " products already imported";
        }
        return message;
    }
}

