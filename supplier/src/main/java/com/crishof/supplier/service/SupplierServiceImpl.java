package com.crishof.supplier.service;

import com.crishof.supplier.model.Supplier;
import com.crishof.supplier.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public void save(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier findById(Long id) {
        return supplierRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Long id, Supplier supplier) {
        Supplier supplier1 = this.findById(id);
        supplier1.setName(supplier.getName());
        supplier1.setCompanyName(supplier.getCompanyName());
        supplier1.setTaxIN(supplier.getTaxIN());

        supplierRepository.save(supplier1);
    }

    @Override
    public void deleteById(Long id) {
        supplierRepository.deleteById(id);
    }
}
