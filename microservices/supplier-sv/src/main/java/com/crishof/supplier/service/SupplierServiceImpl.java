package com.crishof.supplier.service;

import com.crishof.supplier.dto.SupplierRequest;
import com.crishof.supplier.dto.SupplierResponse;
import com.crishof.supplier.exception.DuplicateNameException;
import com.crishof.supplier.exception.SupplierNotFoundException;
import com.crishof.supplier.mapper.SupplierMapper;
import com.crishof.supplier.model.Supplier;
import com.crishof.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public List<SupplierResponse> getAll() {

        return supplierRepository.findAll().stream()
                .map(SupplierMapper::toSupplierResponse)
                .toList();
    }

    @Override
    public SupplierResponse getById(UUID id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(
                () -> new SupplierNotFoundException(id));
        return SupplierMapper.toSupplierResponse(supplier);
    }

    @Override
    public SupplierResponse getByName(String supplierName) {
        Supplier supplier = supplierRepository.findByName(supplierName).orElseThrow(() -> new SupplierNotFoundException(supplierName));

        return SupplierMapper.toSupplierResponse(supplier);
    }

    @Override
    public List<SupplierResponse> getAllByFilter(String filter) {

        return supplierRepository.searchByFilter(filter).stream()
                .map(SupplierMapper::toSupplierResponse)
                .toList();
    }

    @Override
    public SupplierResponse save(SupplierRequest supplierRequest) {

        Supplier supplier = new Supplier(supplierRequest);

        if (supplierRepository.findByName(supplierRequest.getName()).isPresent()) {
            throw new DuplicateNameException("Supplier with name " + supplierRequest.getName() + " already exist");
        }
        if (Objects.equals(supplier.getName(), "")) {
            throw new IllegalArgumentException("Supplier name cannot be empty");
        }
        return new SupplierResponse(supplierRepository.save(supplier));

    }

    @Override
    public SupplierResponse update(UUID id, SupplierRequest supplierRequest) {

        Supplier supplier = supplierRepository.findById(id).orElseThrow(
                () -> new SupplierNotFoundException(id));
        supplier.setName(supplierRequest.getName());
        supplier.setTaxId(supplierRequest.getTaxId());
        supplier.setLegalName(supplierRequest.getLegalName());

        return new SupplierResponse(supplierRepository.save(supplier));
    }

    @Override
    public void deleteById(UUID id) {
        supplierRepository.deleteById(id);
    }
}
