package com.shopping.flipkart.service;

import com.shopping.flipkart.requestdto.AuthRequest;
import com.shopping.flipkart.requestdto.UserRequest;
import com.shopping.flipkart.responseDto.AuthResponse;
import com.shopping.flipkart.responseDto.OtpModel;
import com.shopping.flipkart.responseDto.UserResponse;
import com.shopping.flipkart.util.ResponseStructure;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest);

    ResponseEntity<ResponseStructure<UserResponse>> otpVerify(OtpModel otp);

    ResponseEntity<ResponseStructure<AuthResponse>> login(AuthRequest authRequest, HttpServletResponse httpServletResponse);
}
