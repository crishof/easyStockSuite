package org.crishof.stocksuitemono.repository;

import org.crishof.stocksuitemono.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByNameIgnoreCase(String name);
}
