package org.crishof.stocksuitemono.model;

import jakarta.persistence.*;
import lombok.Data;
import org.crishof.stocksuitemono.enums.UserRole;

import java.util.UUID;

@Data
@Entity
@Table(name = "tbl_user")
public abstract class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private UUID id;
    private String firstName;
    private String lastName;
    private String dni;
    private String email;
    private String phoneNumber;
    private UserRole role;
    @OneToOne(targetEntity = Address.class)
    @JoinColumn(name = "address_id")
    private Address address;
}
