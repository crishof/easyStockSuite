package org.crishof.supplierpricelistsv.repository;

import org.crishof.supplierpricelistsv.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Product findProductByBrandAndCodeAndSupplierId(String brand, String code, UUID supplierId);

    // Supplier null && brand null && filter
    @Query(value = "SELECT * FROM tbl_supplier_product " +
            "WHERE brand ILIKE CONCAT('%', :filter, '%') " +
            "OR model ILIKE CONCAT('%', :filter, '%') " +
            "OR description ILIKE CONCAT('%', :filter, '%')",
            nativeQuery = true)
    List<Product> findAllByBrandContainingOrModelContainingOrDescriptionContaining(@Param("filter") String filter);

    // Supplier null && brand && filter null
    List<Product> findAllByBrand(String brand);

    // Supplier null && brand && filter
    @Query(value = "SELECT * FROM tbl_supplier_product " +
            "WHERE brand LIKE :brand " +
            "OR model ILIKE CONCAT('%', :filter, '%') " +
            "OR description ILIKE CONCAT('%', :filter, '%')",
            nativeQuery = true)
    List<Product> findAllByBrandAndModelContainingOrDescriptionContaining(@Param("brand") String brand, @Param("filter") String filter);

    // Supplier && brand null && filter null
    List<Product> findAllBySupplierId(UUID supplierId);

    // Supplier && brand null && filter
    @Query(value = "SELECT * FROM tbl_supplier_product " +
            "WHERE supplier_id = :supplierId " +
            "AND brand ILIKE CONCAT('%', :filter, '%') " +
            "OR model ILIKE CONCAT('%', :filter, '%') " +
            "OR description ILIKE CONCAT('%', :filter, '%')",
            nativeQuery = true)
    List<Product> findAllBySupplierIdAndBrandContainingOrModelContainingOrDescriptionContaining(UUID supplierId, String filter);

    // Supplier && brand && filter null
    List<Product> findAllBySupplierIdAndBrand(UUID supplierId, String brand);

    // Supplier && brand && filter
    @Query(value = "SELECT * FROM tbl_supplier_product " +
            "WHERE supplier_id = :supplierId " +
            "AND brand LIKE :brand " +
            "AND model ILIKE CONCAT('%', :filter, '%') " +
            "OR description ILIKE CONCAT('%', :filter, '%')",
            nativeQuery = true)
    List<Product> findAllBySupplierIdAndBrandAndCodeContainingOrDescriptionContaining(UUID supplierId, String brand, String filter);
}
