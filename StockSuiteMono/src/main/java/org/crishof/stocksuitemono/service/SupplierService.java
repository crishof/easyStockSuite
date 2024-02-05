package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Supplier;

import java.util.List;

public interface SupplierService {

    List<Supplier> getAll();

    Supplier getById(Long id);

    Supplier findByName(String name);

    void save(Supplier supplier);

    Supplier update(Long id, Supplier supplier);

    void deleteById(Long id);
}
