package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Customer;

public interface CustomerService {

    Customer save(String name, String lastName,String dni);
}
