package com.shopping.flipkart.entity;

import com.shopping.flipkart.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String username;
    private String email;
    private String password;
    private UserRole userrole;
    private boolean isEmailVerified;
    private boolean isDeleted;
}
