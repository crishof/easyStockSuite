package org.crishof.stocksuitemono.repository;

import org.crishof.stocksuitemono.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {

    Optional<Brand> findByNameIgnoreCase(String name);
}
