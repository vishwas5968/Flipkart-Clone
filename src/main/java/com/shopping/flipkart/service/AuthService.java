package com.shopping.flipkart.service;

import com.shopping.flipkart.requestdto.UserRequest;
import com.shopping.flipkart.responseDto.OtpModel;
import com.shopping.flipkart.responseDto.UserResponse;
import com.shopping.flipkart.util.ResponseStructure;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest);

    ResponseEntity<ResponseStructure<UserResponse>> otpVerify(OtpModel otp);
}
