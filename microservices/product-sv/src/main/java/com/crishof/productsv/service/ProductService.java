package com.crishof.productsv.service;

import com.crishof.productsv.dto.InvoiceUpdateRequest;
import com.crishof.productsv.dto.ProductRequest;
import com.crishof.productsv.dto.ProductResponse;
import com.crishof.productsv.dto.SupplierProductRequest;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductResponse> getAll();

    ProductResponse getById(UUID id);

    ProductResponse save(ProductRequest productRequest);

    ProductResponse update(UUID id, ProductRequest productRequest);

    void deleteById(UUID id);

    String getBrandName(UUID uuid);

    List<ProductResponse> getAllByFilter(String filter);

    List<ProductResponse> getAllByFilterAndSupplier(String filter, UUID supplierId);

    List<ProductResponse> getAllByFilterAndStock(String filter);

    boolean checkProductsByBrand(UUID brandId);

    Long countProductsByBrand(UUID brandId);

    void removeCategory(UUID categoryId);

    String updateFromInvoice(InvoiceUpdateRequest invoiceUpdateRequest);

    String updateFromCustomerInvoice(InvoiceUpdateRequest invoiceUpdateRequest);

    String importSupplierProducts(List<SupplierProductRequest> productList);
}