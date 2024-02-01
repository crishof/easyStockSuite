package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Supplier;

import java.util.List;

public interface SupplierService {

    List<Supplier> findAll();

    Supplier findtById(Long id);

    void save(Supplier supplier);

    Supplier update(Long id, Supplier supplier);

    void deleteById(Long id);
}
