package com.shopping.flipkart.controller;

import com.shopping.flipkart.requestdto.AuthRequest;
import com.shopping.flipkart.requestdto.UserRequest;
import com.shopping.flipkart.responseDto.AuthResponse;
import com.shopping.flipkart.responseDto.OtpModel;
import com.shopping.flipkart.responseDto.UserResponse;
import com.shopping.flipkart.serviceImpl.AuthServiceImpl;
import com.shopping.flipkart.util.ResponseStructure;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AuthController {

    private AuthServiceImpl authService;

    @PostMapping(path = "/user")
    public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@RequestBody UserRequest userRequest) throws MessagingException {
        return authService.registerUser(userRequest);
    }

    @PostMapping(path = "/verify-otp")
    public ResponseEntity<ResponseStructure<UserResponse>> otpVerify(@RequestBody OtpModel otpModel){

        return authService.otpVerify(otpModel);
    }
    @PostMapping(path = "/login")
    public ResponseEntity<ResponseStructure<AuthResponse>> login(@RequestBody AuthRequest authRequest, HttpServletResponse httpServletResponse){
        return  authService.login(authRequest, httpServletResponse);
    }
}
