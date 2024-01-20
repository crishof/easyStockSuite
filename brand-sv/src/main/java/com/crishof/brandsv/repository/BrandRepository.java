package com.crishof.brandsv.repository;

import com.crishof.brandsv.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
