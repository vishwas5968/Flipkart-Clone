package com.shopping.flipkart.controller;

import com.shopping.flipkart.requestdto.UserRequest;
import com.shopping.flipkart.responseDto.UserResponse;
import com.shopping.flipkart.serviceImpl.AuthServiceImpl;
import com.shopping.flipkart.util.ResponseStructure;
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
    public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@RequestBody UserRequest userRequest){
        return authService.registerUser(userRequest);
    }
}
