package com.crishof.supplier.repository;

import com.crishof.supplier.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

    Supplier findByName(String name);

    Optional<Supplier> findByNameIgnoreCase(String name);
}