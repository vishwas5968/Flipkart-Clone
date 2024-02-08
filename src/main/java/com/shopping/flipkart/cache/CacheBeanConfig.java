package com.shopping.flipkart.cache;

import com.shopping.flipkart.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
@Configuration
public class CacheBeanConfig {
    @Bean
    public CacheStore<User> userCacheStore(){
        return new CacheStore<User>(Duration.ofMinutes(5));
    }
    @Bean
    public CacheStore<Integer> otp(){
        return new CacheStore<Integer>(Duration.ofMinutes(5));
    }

}
