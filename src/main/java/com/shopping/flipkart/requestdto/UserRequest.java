package com.shopping.flipkart.requestdto;

import com.shopping.flipkart.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserRequest {

    private String email;
    private String password;
    private UserRole userrole;

}
