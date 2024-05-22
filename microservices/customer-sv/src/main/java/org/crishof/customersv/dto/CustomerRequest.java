package org.crishof.customersv.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerRequest {


    private String name;
    private String lastname;
    private String dni;
    private String taxId;
    private String email;
    private String phone;

    private AddressRequest addressRequest;
}

