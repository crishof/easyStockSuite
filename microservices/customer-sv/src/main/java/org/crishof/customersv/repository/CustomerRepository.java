package org.crishof.customersv.repository;

import org.crishof.customersv.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Customer findByDni(String dni);

    Customer findByEmail(String email);

    List<Customer> findAllByNameAndLastname(String name, String lastname);
}
