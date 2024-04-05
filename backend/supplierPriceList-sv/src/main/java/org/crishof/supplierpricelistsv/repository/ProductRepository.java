package org.crishof.supplierpricelistsv.repository;

import org.crishof.supplierpricelistsv.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findProductByBrandAndCodeAndSupplierId(String brand, String code, UUID supplierId);
}
