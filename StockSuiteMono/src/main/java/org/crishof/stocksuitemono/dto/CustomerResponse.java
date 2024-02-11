package org.crishof.stocksuitemono.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.stocksuitemono.model.Customer;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private UUID id;
    private String name;
    private String lastName;
    private String email;
    private String taxId;

    public CustomerResponse(Customer customer){

        this.id = customer.getId();
        this.name = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.taxId = customer.getTaxId();
    }

}
