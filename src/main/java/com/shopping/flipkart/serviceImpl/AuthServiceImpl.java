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

    @Override
    public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest) {
//        if (userRepo.existsByEmail(userRequest.getEmail())){
//            throw new ConstraintViolationException("User with email already present",HttpStatus.ALREADY_REPORTED.value(), "No 2 users can have same email");
//        }
//        else {
            User user = userRepo.findByUsername(userRequest.getEmail().split("@")[0]).map(user1 -> {
                if (user1.isEmailVerified()) {
                    throw new ConstraintViolationException("User with email already present", HttpStatus.ALREADY_REPORTED.value(), "No 2 users can have same email");
                } else {
                    // Verify using OTP
                }
                return user1;
            }).orElseGet(() -> saveUser(userRequest));
            responseStructure.setData(mapToUserResponse(user));
            responseStructure.setStatus(HttpStatus.CREATED.value());
            responseStructure.setMessage("Please verify through the OTP sent on your email");
            return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
//        }
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder().email(user.getEmail()).username(user.getUsername()).userId(user.getUserId()).userrole(user.getUserrole()).isDeleted(user.isDeleted()).isEmailVerified(user.isEmailVerified()).build();
    }

    public User saveUser(UserRequest userRequest) {
        User user=null;
        switch (userRequest.getUserrole()) {
            case SELLER -> {
                user = sellerRepo.save((Seller) mapToUser(userRequest));
            }
            case CUSTOMER -> {
                 user = customerRepo.save((Customer) mapToUser(userRequest));
            }
            default ->
                    throw new ConstraintViolationException("No UserRole mentioned", HttpStatus.BAD_REQUEST.value(), "");
        }
        return user;
    }

    public <T extends User> T mapToUser(UserRequest userRequest) {
        User user = null;
        switch (userRequest.getUserrole()) {
            case CUSTOMER -> {
                user = new Customer();
            }
            case SELLER -> {
                user = new Seller();
            }
        }
        user.setUsername(userRequest.getEmail().split("@")[0]);
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setUserrole(userRequest.getUserrole());
        user.setDeleted(false);
        user.setEmailVerified(false);
        return (T) user;
    }
}
