package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Supplier;
import org.crishof.stocksuitemono.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier getById(UUID id) {
        return supplierRepository.findById(id).orElse(null);
    }

    @Override
    public Supplier findByName(String name) {
        return supplierRepository.findByName(name);
    }


    @Override
    public void save(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    @Override
    public Supplier update(UUID id, Supplier supplier) {

        Supplier supplier1 = this.getById(id);

        supplier1.setName(supplier.getName());
        supplier1.setTaxId(supplier1.getTaxId());
        supplier1.setLegalName(supplier.getLegalName());

        return supplierRepository.save(supplier1);
    }

    @Override
    public void deleteById(UUID id) {
        supplierRepository.deleteById(id);
    }
}
