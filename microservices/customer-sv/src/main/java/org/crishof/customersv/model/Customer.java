package org.crishof.customersv.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_customer")
@Builder
public class Customer {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String lastname;
    private String dni;
    private String taxId;
    private String email;
    private String phone;
    private UUID addressId;

}
