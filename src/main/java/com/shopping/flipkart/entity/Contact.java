package com.shopping.flipkart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopping.flipkart.enums.Priority;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contactId;
    private String name;
    private long contactNumber;
    private Priority priority;

    @JsonIgnore
    @ManyToOne
    private Address address;
}
