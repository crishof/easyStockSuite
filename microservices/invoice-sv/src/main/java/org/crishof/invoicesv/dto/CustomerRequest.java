package org.crishof.invoicesv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {

    private String name;
    private String lastname;
    private String dni;
    private String taxId;
    private String email;
    private String phone;
}
