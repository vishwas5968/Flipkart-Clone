package com.shopping.flipkart.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tokenId;
    private String token;
    private boolean isBlocked;
    private LocalDateTime expiration;

    @ManyToOne
    private User user;
}