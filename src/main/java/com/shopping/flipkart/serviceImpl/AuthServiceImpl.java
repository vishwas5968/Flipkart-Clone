package com.shopping.flipkart.serviceImpl;

import com.shopping.flipkart.cache.CacheStore;
import com.shopping.flipkart.entity.Customer;
import com.shopping.flipkart.entity.Seller;
import com.shopping.flipkart.entity.User;
import com.shopping.flipkart.exception.ConstraintViolationException;
import com.shopping.flipkart.repo.UserRepo;
import com.shopping.flipkart.requestdto.UserRequest;
import com.shopping.flipkart.responseDto.OtpModel;
import com.shopping.flipkart.responseDto.UserResponse;
import com.shopping.flipkart.service.AuthService;
import com.shopping.flipkart.util.MessageStructure;
import com.shopping.flipkart.util.ResponseStructure;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepo userRepo;
    private ResponseStructure<UserResponse> responseStructure;
    private CacheStore<Integer> otpCacheStore;
    private CacheStore<User> userCacheStore;
    private JavaMailSender javaMailSender;

    @Override
    public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest) {
        if (userRepo.existsByEmail(userRequest.getEmail())){
            throw new ConstraintViolationException("User with email already present",HttpStatus.ALREADY_REPORTED.value(), "No 2 users can have same email");
        }
        int otp= (int) Math.round(Math.random()*1000000);
        System.out.println(otp);
        User user=mapToUser(userRequest);
        try {
            sendOtpToMail(user, otp);
        } catch (MessagingException e) {
            log.error("Email address does not exist");
        }
        otpCacheStore.setCache(user.getEmail(), otp);
        userCacheStore.setCache(user.getEmail(), user);
        responseStructure.setData(mapToUserResponse(user));
        responseStructure.setStatus(HttpStatus.CREATED.value());
        responseStructure.setMessage("Please verify through the OTP sent on your email"+otp);
        return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<UserResponse>> otpVerify(OtpModel otp){
        Integer exOtp=otpCacheStore.getCache(otp.getEmail());
        User user=userCacheStore.getCache(otp.getEmail());
        if (exOtp == null) throw new ConstraintViolationException("OTP is null", HttpStatus.NO_CONTENT.value(), "");
        if (user == null) throw new ConstraintViolationException("User is null", HttpStatus.NO_CONTENT.value(), "");
        if (exOtp.equals(otp.getOtp())){
            user.setEmailVerified(true);
            user=userRepo.save(user);
            responseStructure.setData(mapToUserResponse(user));
            responseStructure.setMessage("User saved successfully");
            responseStructure.setStatus(HttpStatus.CREATED.value());
        }
        return new ResponseEntity<>(responseStructure,HttpStatus.ACCEPTED);
    }
    @Async
    public void sendMail(MessageStructure message) throws MessagingException {
        MimeMessage mimeMessage= javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage);
        messageHelper.setTo(message.getTo());
        messageHelper.setSubject(message.getSubject());
        messageHelper.setSentDate(message.getSentDate());
        messageHelper.setText(message.getText(),true);
        javaMailSender.send(mimeMessage);
    }

    public void sendSuccessfulRegistrationMail(User user) throws MessagingException {
        sendMail(MessageStructure.builder().to(user.getEmail()).text("We have successfully registered your email").build());
    }

    public void sendOtpToMail(User user,Integer otp) throws MessagingException{
        sendMail(MessageStructure.builder().to(user.getEmail()).text("Hey, "+user.getUsername()+" Good to see that you are interested in Flipkart, "+"Complete your registration using the OTP <br>"+"<h1>"+otp+"<h1>"+"Note: The OTP expires in 1 minute"+"<br><br>"+"with best regards<br>"+"Flipkart").sentDate(new Date()).subject("Complete your Registration to Flipkart").build());
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder().email(user.getEmail()).username(user.getUsername()).userId(user.getUserId()).userrole(user.getUserrole()).isDeleted(user.isDeleted()).isEmailVerified(user.isEmailVerified()).build();
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
