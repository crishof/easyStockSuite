package org.crishof.addresssv.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressRequest {

    private String street;
    private String houseNumber;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
