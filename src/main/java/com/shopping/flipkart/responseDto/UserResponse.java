package com.shopping.flipkart.responseDto;

import com.shopping.flipkart.enums.UserRole;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private int userId;
    private String username;
    private String email;
    private UserRole userrole;
    private boolean isEmailVerified;
    private boolean isDeleted;
}
