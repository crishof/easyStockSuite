package org.crishof.stocksuitemono.repository;

import org.crishof.stocksuitemono.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p FROM Product p WHERE LOWER(p.brand.name) LIKE lower(concat('%', :filter, '%') ) ")
    List<Product> findAllByBrandName(@Param("filter") String filter);

    List<Product> findAllByModelContainingIgnoreCase(String filter);

    List<Product> findAllByDescriptionContainingIgnoreCase(String filter);
}
