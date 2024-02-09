package com.shopping.flipkart.responseDto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Builder
public class AuthResponse {

    private int userId;
    private String username;
    private String userRole;
    private LocalDateTime accessExpiration;
    private LocalDateTime refreshExpiration;
    private boolean isAuthenticated;

}
