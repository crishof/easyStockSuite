package com.crishof.productsv.repository;

import com.crishof.productsv.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

//    @Query("SELECT p FROM Product p WHERE LOWER(p.brand.name) LIKE lower(concat('%', :filter, '%') ) ")
//    List<Product> findAllByBrandName(@Param("filter") String filter);

    List<Product> findAllByBrandId(UUID id);

    List<Product> findAllByModelContainingIgnoreCase(String filter);

    List<Product> findAllByDescriptionContainingIgnoreCase(String filter);

    Long countByBrandId(UUID brandId);

    List<Product> findAllByCategoryId(UUID categoryID);
}
