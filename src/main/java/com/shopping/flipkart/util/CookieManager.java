package com.shopping.flipkart.util;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieManager {

    @Value("${myapp.domain}")
    private String domain;

    public Cookie configureCookie(Cookie cookie, int expirationInSeconds){
        cookie.setDomain(domain);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(expirationInSeconds);
        return cookie;
    }

    public Cookie invalidateCookie(Cookie cookie){
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }

}
