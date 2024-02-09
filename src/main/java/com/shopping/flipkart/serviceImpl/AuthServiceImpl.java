package com.shopping.flipkart.serviceImpl;

import com.shopping.flipkart.cache.CacheStore;
import com.shopping.flipkart.entity.*;
import com.shopping.flipkart.exception.ConstraintViolationException;
import com.shopping.flipkart.repo.AccessTokenRepo;
import com.shopping.flipkart.repo.RefreshTokenRepo;
import com.shopping.flipkart.repo.UserRepo;
import com.shopping.flipkart.requestdto.AuthRequest;
import com.shopping.flipkart.requestdto.UserRequest;
import com.shopping.flipkart.responseDto.AuthResponse;
import com.shopping.flipkart.responseDto.OtpModel;
import com.shopping.flipkart.responseDto.UserResponse;
import com.shopping.flipkart.security.JwtService;
import com.shopping.flipkart.service.AuthService;
import com.shopping.flipkart.util.CookieManager;
import com.shopping.flipkart.util.MessageStructure;
import com.shopping.flipkart.util.ResponseStructure;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private UserRepo userRepo;

    private ResponseStructure<UserResponse> userResponseStructure;
    private ResponseStructure<AuthResponse> authResponseStructure;
    private CacheStore<Integer> otpCacheStore;
    private CacheStore<User> userCacheStore;
    private JavaMailSender javaMailSender;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private CookieManager cookieManager;
    @Value("${myapp.access.expiry}")
    private int accessExpiryInSeconds;
    @Value("{myapp.refresh.expiry}")
    private int refreshExpiryInSeconds;
    private JwtService jwtService;
    private AccessTokenRepo accessTokenRepo;
    private RefreshTokenRepo refreshTokenRepo;

    public AuthServiceImpl(UserRepo userRepo, ResponseStructure<UserResponse> userResponseStructure, CacheStore<Integer> otpCacheStore, CacheStore<User> userCacheStore, JavaMailSender javaMailSender, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, CookieManager cookieManager, int accessExpiryInSeconds, int refreshExpiryInSeconds, JwtService jwtService, AccessTokenRepo accessTokenRepo, RefreshTokenRepo refreshTokenRepo) {

        this.userRepo = userRepo;
        this.userResponseStructure = userResponseStructure;
        this.otpCacheStore = otpCacheStore;
        this.userCacheStore = userCacheStore;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.cookieManager = cookieManager;
        this.accessExpiryInSeconds = accessExpiryInSeconds;
        this.refreshExpiryInSeconds = refreshExpiryInSeconds;
        this.jwtService = jwtService;
        this.accessTokenRepo = accessTokenRepo;
        this.refreshTokenRepo = refreshTokenRepo;

    }

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
        userResponseStructure.setData(mapToUserResponse(user));
        userResponseStructure.setStatus(HttpStatus.CREATED.value());
        userResponseStructure.setMessage("Please verify through the OTP sent on your email"+otp);
        return new ResponseEntity<>(userResponseStructure, HttpStatus.CREATED);
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
            userResponseStructure.setData(mapToUserResponse(user));
            userResponseStructure.setMessage("User saved successfully");
            userResponseStructure.setStatus(HttpStatus.CREATED.value());
        }
        return new ResponseEntity<>(userResponseStructure,HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ResponseStructure<AuthResponse>> login(AuthRequest authRequest, HttpServletResponse httpServletResponse) {
        String username = authRequest.getEmail().split("@")[0];
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, authRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        if (!authentication.isAuthenticated()){
            throw new ConstraintViolationException("", 10, "");
        }
        else {
            //Generating the cookies and AuthResponse and returning to the client
            User user = userRepo.findByUsername(username).get();
            grantAccess(httpServletResponse, user);
            authResponseStructure.setStatus(HttpStatus.OK.value());
            authResponseStructure.setData(AuthResponse.builder().userId(user.getUserId()).userRole(user.getUserrole().name()).isAuthenticated(true).accessExpiration(LocalDateTime.now().plusSeconds(accessExpiryInSeconds)).refreshExpiration(LocalDateTime.now().plusSeconds(refreshExpiryInSeconds)).build());
            authResponseStructure.setMessage("User Successfully Authenticated");
            return new ResponseEntity<>(authResponseStructure, HttpStatus.ACCEPTED);
        }
    }

    private void grantAccess(HttpServletResponse httpServletResponse,User user){
        //Generating accessTokens, refreshTokens
        String accessTokens = jwtService.generateAccessTokens(user.getUsername());
        String refreshTokens = jwtService.generateRefreshTokens(user.getUsername());

        //Adding access and refresh tokens cookies to the response
        Cookie cookie = cookieManager.configureCookie(new Cookie("at", accessTokens), accessExpiryInSeconds);
        httpServletResponse.addCookie(cookie);
        httpServletResponse.addCookie(cookieManager.configureCookie(new Cookie("rt",refreshTokens), refreshExpiryInSeconds));

        //Saving accessTokens, refreshTokens in the repo
        accessTokenRepo.save(AccessToken.builder().token(accessTokens).isBlocked(false).expiration(LocalDateTime.now().plusSeconds(accessExpiryInSeconds)).build());
        refreshTokenRepo.save(RefreshToken.builder().token(refreshTokens).isBlocked(false).expiration(LocalDateTime.now().plusSeconds(refreshExpiryInSeconds)).build());
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
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setUserrole(userRequest.getUserrole());
        user.setDeleted(false);
        user.setEmailVerified(false);
        return (T) user;
    }

}
