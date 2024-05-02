package com.crishof.supplier.service;

import com.crishof.supplier.dto.SupplierRequest;
import com.crishof.supplier.dto.SupplierResponse;
import com.crishof.supplier.model.Supplier;

import java.util.List;
import java.util.UUID;

public interface SupplierService {

    List<SupplierResponse> getAll();

    SupplierResponse getById(UUID id);

    List<SupplierResponse> getAllByFilter(String filter);

    SupplierResponse save(SupplierRequest supplierRequest);

    SupplierResponse update(UUID id, SupplierRequest supplier);

    void deleteById(UUID id);

    SupplierResponse getByName(String supplierName);
}
