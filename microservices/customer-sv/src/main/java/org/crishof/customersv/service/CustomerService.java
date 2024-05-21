package org.crishof.customersv.service;


import org.crishof.customersv.dto.CustomerRequest;
import org.crishof.customersv.dto.CustomerResponse;
import org.crishof.customersv.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<CustomerResponse> getAll();

    CustomerResponse getById(UUID id);

    List<CustomerResponse> getAllByNameOrLastname(String name, String lastname);

    CustomerResponse getByEmail(String email);

    CustomerResponse getByDni(String dni);

    CustomerResponse update(UUID customerId, CustomerRequest customerRequest);

    CustomerResponse save(CustomerRequest customerRequest);

    String deleteById(UUID id);
}
