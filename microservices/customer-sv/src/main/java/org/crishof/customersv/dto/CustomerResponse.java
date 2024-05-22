package org.crishof.customersv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private UUID id;
    private String name;
    private String lastname;
    private String dni;
    private String taxId;
    private String email;
    private String phone;
    private UUID addressId;
}
