package com.crishof.supplier.repository;

import com.crishof.supplier.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

    Optional<Supplier> findByName(String name);

    @Query("SELECT s FROM Supplier s WHERE lower(s.name) LIKE lower(concat('%', :filter, '%')) OR lower(s.legalName) LIKE lower(concat('%', :filter, '%'))")
    List<Supplier> searchByFilter(@Param("filter") String filter);

}
