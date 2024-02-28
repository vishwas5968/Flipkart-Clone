package com.shopping.flipkart.entity;

import com.shopping.flipkart.enums.AddressType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;
    private String streetAddress;
    private String streetAddressAdditional;
    private String country;
    private String state;
    private String city;
    private int pincode;
    private AddressType addressType;

    @OneToMany(mappedBy = "address")
    private List<Contact> contacts;

}
