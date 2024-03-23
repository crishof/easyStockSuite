package org.crishof.addresssv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_address")
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private UUID id;
    private String street;
    private String houseNumber;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
