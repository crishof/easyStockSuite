package org.crishof.stocksuitemono.repository;

import org.crishof.stocksuitemono.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

    Supplier findByName(String name);
}
