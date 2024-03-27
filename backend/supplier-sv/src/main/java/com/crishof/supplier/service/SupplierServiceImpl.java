package com.crishof.supplier.service;

import com.crishof.supplier.dto.SupplierRequest;
import com.crishof.supplier.dto.SupplierResponse;
import com.crishof.supplier.exception.DuplicateNameException;
import com.crishof.supplier.exception.SupplierNotFoundException;
import com.crishof.supplier.model.Supplier;
import com.crishof.supplier.modelMapper.SupplierMapper;
import com.crishof.supplier.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    public SupplierResponse getByName(String name) {

        Supplier supplier = supplierRepository.findByNameIgnoreCase(name).orElseThrow(() -> new SupplierNotFoundException("Supplier with name " + name + " not found"));
        return SupplierMapper.toSupplierResponse(supplier);
    }

    @Override
    public SupplierResponse save(SupplierRequest supplierRequest) {

        Supplier supplier = new Supplier(supplierRequest);

        if (supplierRepository.findByNameIgnoreCase(supplierRequest.getName()).isPresent()) {
            throw new DuplicateNameException("Supplier with name " + supplierRequest.getName() + " already exist");
        }
        if (Objects.equals(supplier.getName(), "")) {
            throw new IllegalArgumentException("Supplier name cannot be empty");
        }
        return new SupplierResponse(supplierRepository.save(supplier));

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
