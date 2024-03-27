package com.crishof.supplier.service;

import com.crishof.supplier.dto.SupplierRequest;
import com.crishof.supplier.dto.SupplierResponse;
import com.crishof.supplier.model.Supplier;

import java.util.List;
import java.util.UUID;

public interface SupplierService {

    List<Supplier> getAll();

    Supplier getById(UUID id);

    SupplierResponse getByName(String name);

    Supplier findByName(String name);

    SupplierResponse save(SupplierRequest supplierRequest);

    Supplier update(UUID id, Supplier supplier);

    void deleteById(UUID id);
}
