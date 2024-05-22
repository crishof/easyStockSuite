package org.crishof.customersv.repository;

import org.crishof.customersv.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Customer findByDni(String dni);

    Customer findByEmail(String email);

    List<Customer> findAllByNameAndLastname(String name, String lastname);

    @Query("SELECT c FROM Customer c WHERE " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
            "LOWER(c.lastname) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
            "LOWER(c.email) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
            "LOWER(c.dni) LIKE LOWER(CONCAT('%', :filter, '%'))")
    List<Customer> findAllByFilter(@Param("filter") String filter);
}
