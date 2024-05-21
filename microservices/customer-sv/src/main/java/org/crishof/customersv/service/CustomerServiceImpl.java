package org.crishof.customersv.service;

import lombok.RequiredArgsConstructor;
import org.crishof.customersv.apiClient.AddressAPIClient;
import org.crishof.customersv.dto.CustomerRequest;
import org.crishof.customersv.dto.CustomerResponse;
import org.crishof.customersv.exception.CustomerNotFoundException;
import org.crishof.customersv.model.Customer;
import org.crishof.customersv.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressAPIClient addressAPIClient;

    @Override
    public List<CustomerResponse> getAll() {
        return customerRepository.findAll().stream().map(this::toCustomerResponse).toList();
    }

    @Override
    public CustomerResponse getById(UUID id) {
        return customerRepository.findById(id).map(this::toCustomerResponse).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public List<CustomerResponse> getAllByNameOrLastname(String name, String lastname) {
        return customerRepository.findAllByNameAndLastname(name, lastname).stream().map(this::toCustomerResponse).toList();
    }

    @Override
    public List<CustomerResponse> getAllByFilter(String filter) {
        return customerRepository.findAllByFilter(filter).stream().map(this::toCustomerResponse).toList();
    }

    @Override
    public CustomerResponse getByEmail(String email) {
        return this.toCustomerResponse(customerRepository.findByEmail(email));
    }

    @Override
    public CustomerResponse getByDni(String dni) {
        return this.toCustomerResponse(customerRepository.findByDni(dni));
    }

    @Override
    public CustomerResponse update(UUID customerId, CustomerRequest customerRequest) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        customer.setName(customerRequest.getName());
        customer.setLastname(customerRequest.getLastname());
        customer.setEmail(customerRequest.getEmail());
        customer.setDni(customerRequest.getDni());
        customer.setTaxId(customerRequest.getTaxId());
        customer.setPhone(customerRequest.getPhone());

        return this.toCustomerResponse(customerRepository.save(customer));
    }

    @Override
    public CustomerResponse save(CustomerRequest customerRequest) {

        Customer customer = this.toCustomer(customerRequest);

        UUID addressId = addressAPIClient.createAndGetId(customerRequest.getAddressRequest()).getBody();

        System.out.println("customerRequest = " + customerRequest.getAddressRequest());
        System.out.println("addressId = " + addressId);

        customer.setAddressId(addressId);

        System.out.println("customer = " + customer.getAddressId());

        return this.toCustomerResponse(customerRepository.save(customer));
    }

    @Override
    public String deleteById(UUID id) {

        UUID addressId = this.getById(id).getAddressId();

        ResponseEntity<String> response = addressAPIClient.delete(addressId);
        if (response.getStatusCode() == HttpStatus.OK) {
            customerRepository.deleteById(id);
            return "Customer deleted successfully";
        } else {
            throw new IllegalArgumentException("Failed to delete Address, customer not deleted");
        }
    }

    private Customer toCustomer(CustomerRequest customerRequest) {
        return Customer.builder()
                .name(customerRequest.getName())
                .lastname(customerRequest.getLastname())
                .email(customerRequest.getEmail())
                .dni(customerRequest.getDni())
                .taxId(customerRequest.getTaxId())
                .phone(customerRequest.getPhone())
                .build();
    }

    private CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .lastname(customer.getLastname())
                .email(customer.getEmail())
                .dni(customer.getDni())
                .taxId(customer.getTaxId())
                .phone(customer.getPhone())
                .addressId(customer.getAddressId())
                .build();
    }
}
