package com.shopping.flipkart.serviceImpl;

import com.shopping.flipkart.entity.Customer;
import com.shopping.flipkart.entity.Seller;
import com.shopping.flipkart.entity.User;
import com.shopping.flipkart.exception.ConstraintViolationException;
import com.shopping.flipkart.repo.CustomerRepo;
import com.shopping.flipkart.repo.SellerRepo;
import com.shopping.flipkart.repo.UserRepo;
import com.shopping.flipkart.requestdto.UserRequest;
import com.shopping.flipkart.responseDto.UserResponse;
import com.shopping.flipkart.service.AuthService;
import com.shopping.flipkart.util.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepo userRepo;
    private SellerRepo sellerRepo;
    private CustomerRepo customerRepo;
    private ResponseStructure<UserResponse> responseStructure;

    
}
