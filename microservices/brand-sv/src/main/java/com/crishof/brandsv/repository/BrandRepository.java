package com.crishof.brandsv.repository;

import com.crishof.brandsv.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {

    Optional<Brand> findByNameIgnoreCase(String name);

    Optional<Brand> findBrandByNameContainingIgnoreCase(String filter);

    List<Brand> findAllByNameContainingIgnoreCase(String filter);
}
