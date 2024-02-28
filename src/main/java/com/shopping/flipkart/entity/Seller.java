package com.shopping.flipkart.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller extends User{

    @OneToOne
    private Store store;

    @OneToOne
    private Address address;

}
