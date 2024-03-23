package com.crishof.supplier.service;

import com.crishof.supplier.model.Supplier;

import java.util.List;
import java.util.UUID;

public interface SupplierService {

    List<Supplier> getAll();

    Supplier getById(UUID id);

    Supplier findByName(String name);

    void save(Supplier supplier);

    Supplier update(UUID id, Supplier supplier);

    void deleteById(UUID id);
}
