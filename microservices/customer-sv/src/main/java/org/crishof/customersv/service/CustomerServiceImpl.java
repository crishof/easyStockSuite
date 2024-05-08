package org.crishof.customersv.service;

import lombok.RequiredArgsConstructor;
import org.crishof.customersv.dto.CustomerResponse;
import org.crishof.customersv.exception.CustomerNotFoundException;
import org.crishof.customersv.model.Customer;
import org.crishof.customersv.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<CustomerResponse> getAll() {

        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::toCustomerResponse).toList();
    }

    @Override
    public CustomerResponse getById(UUID id) {
        return customerRepository.findById(id).map(this::toCustomerResponse).orElseThrow(() -> new CustomerNotFoundException("Customer with id: " + id + "does not exist"));
    }

    @Override
    public List<Customer> getByName(String name) {
        return customerRepository.findAll().stream().filter(customer -> customer.getName().equalsIgnoreCase(name)).toList();
    }

    @Override
    public List<Customer> getByLastName(String lastName) {
        return customerRepository.findAll().stream().filter(customer -> customer.getLastname().equalsIgnoreCase(lastName)).toList();
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
        customer.setName(name);
        customer.setLastname(lastName);
        customer.setDni(dni);

        return customerRepository.save(customer);
    }

    private CustomerResponse toCustomerResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setName(customer.getName());
        response.setLastName(customer.getLastname());
        return response;
    }
}
