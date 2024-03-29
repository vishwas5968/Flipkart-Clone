package com.shopping.flipkart.service;

import com.shopping.flipkart.requestdto.AuthRequest;
import com.shopping.flipkart.requestdto.UserRequest;
import com.shopping.flipkart.responseDto.AuthResponse;
import com.shopping.flipkart.responseDto.OtpModel;
import com.shopping.flipkart.responseDto.UserResponse;
import com.shopping.flipkart.util.ResponseStructure;
import com.shopping.flipkart.util.SimpleResponseStructure;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest);

    ResponseEntity<ResponseStructure<UserResponse>> otpVerify(OtpModel otp);

    ResponseEntity<ResponseStructure<AuthResponse>> login(String accessToken, String refreshToken,AuthRequest authRequest, HttpServletResponse httpServletResponse);

//    ResponseEntity<ResponseStructure<String>> logout(HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<SimpleResponseStructure> logout(String accessToken, String refreshToken, HttpServletResponse response);

    ResponseEntity<SimpleResponseStructure> revokeAll();

    ResponseEntity<SimpleResponseStructure> revokeOther(String accessToken, String refreshToken);

    ResponseEntity<ResponseStructure<AuthResponse>> refreshToken(String accessToken, String refreshToken, HttpServletResponse httpServletResponse);
}
