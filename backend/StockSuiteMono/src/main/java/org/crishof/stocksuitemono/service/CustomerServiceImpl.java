package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.dto.CustomerResponse;
import org.crishof.stocksuitemono.exception.notFound.CustomerNotFoundException;
import org.crishof.stocksuitemono.model.Customer;
import org.crishof.stocksuitemono.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<CustomerResponse> getAll() {

        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(CustomerResponse::new).toList();
    }

    @Override
    public CustomerResponse getById(UUID id) {
        return customerRepository.findById(id).map(CustomerResponse::new).orElseThrow(() -> new CustomerNotFoundException("Customer with id: " + id + "does not exist"));
    }

    @Override
    public List<Customer> getByName(String name) {
        return customerRepository.findAll().stream().filter(customer -> customer.getFirstName().equalsIgnoreCase(name)).toList();
    }

    @Override
    public List<Customer> getByLastName(String lastName) {
        return customerRepository.findAll().stream().filter(customer -> customer.getLastName().equalsIgnoreCase(lastName)).toList();
    }

    @Override
    public Customer getByDni(String dni) {
        Customer customer = customerRepository.findByDni(dni);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with dni: " + dni + " does not exist");
        }
        return customer;
    }

    @Override
    public Customer update(UUID id, Customer customer) {
        return null;
    }

    @Override
    public Customer save(String name, String lastName, String dni) {

        Customer customer = new Customer();
        customer.setFirstName(name);
        customer.setLastName(lastName);
        customer.setDni(dni);

        return customerRepository.save(customer);
    }
}
