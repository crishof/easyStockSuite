package com.crishof.supplier.service;

import com.crishof.supplier.model.Supplier;

import java.util.List;

public interface SupplierService {

    void save(Supplier supplier);

    List<Supplier> getAll();

    Supplier findById(Long id);

    void update(Long id, Supplier supplier);

    void deleteById(Long id);
}
