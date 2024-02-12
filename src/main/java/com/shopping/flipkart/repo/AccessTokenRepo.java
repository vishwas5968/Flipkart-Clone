package com.shopping.flipkart.repo;

import com.shopping.flipkart.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AccessTokenRepo extends JpaRepository<AccessToken,Long> {

    AccessToken findByToken(String at);

    List<AccessToken> findByExpirationBefore(LocalDateTime now);
}
