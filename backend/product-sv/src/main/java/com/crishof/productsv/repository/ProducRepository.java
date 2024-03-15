package com.crishof.productsv.repository;

import com.crishof.productsv.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducRepository extends JpaRepository<Product, Long> {
}
