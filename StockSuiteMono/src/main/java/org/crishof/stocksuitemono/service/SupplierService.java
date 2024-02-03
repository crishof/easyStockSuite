package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Supplier;

import java.util.List;

public interface SupplierService {

    List<Supplier> findAll();

    Supplier findById(Long id);

    Supplier findByName(String name);

    void save(Supplier supplier);

    Supplier update(Long id, Supplier supplier);

    void deleteById(Long id);
}
