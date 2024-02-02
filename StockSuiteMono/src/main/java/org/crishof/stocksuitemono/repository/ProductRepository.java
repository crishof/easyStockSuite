package org.crishof.stocksuitemono.repository;

import org.crishof.stocksuitemono.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductByCode(String code);
}
