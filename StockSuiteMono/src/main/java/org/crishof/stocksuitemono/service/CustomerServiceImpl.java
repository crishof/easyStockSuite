package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Customer;
import org.crishof.stocksuitemono.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;
    @Override
    public Customer save(String name, String lastName, String dni) {

        Customer customer = new Customer();
        customer.setFirstName(name);
        customer.setLastName(lastName);
        customer.setDni(dni);

        return customerRepository.save(customer);
    }
}
