package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.dto.CustomerResponse;
import org.crishof.stocksuitemono.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<CustomerResponse> getAll();

    CustomerResponse getById(UUID id);

    List<Customer> getByName(String name);

    List<Customer> getByLastName(String lastName);

    Customer getByDni(String dni);

    Customer update(UUID id, Customer customer);

    Customer save(String name, String lastName, String dni);
}
