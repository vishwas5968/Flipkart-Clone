package com.shopping.flipkart.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JwtService {
    @Value("${myapp.secret}")
    private String secret;
    @Value("${myapp.access.expiry}")
    private Long accessExpirationInSeconds;
    @Value("${myapp.refresh.expiry}")
    private Long refreshExpirationInSeconds;

    public String generateAccessTokens(String username){
        return generateJwt(username,new HashMap<String,Object>(),accessExpirationInSeconds);
    }

    public String generateRefreshTokens(String username){
        return generateJwt(username,new HashMap<String,Object>(),refreshExpirationInSeconds);
    }

    private String generateJwt(String username, Map<String,Object> claims, Long expiry){
        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+ expiry *1000l)).signWith(getSignatureKey(), SignatureAlgorithm.HS512).compact();
    }

    private Key getSignatureKey(){
        byte[] secretBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }

}