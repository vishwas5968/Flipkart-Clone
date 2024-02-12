package com.shopping.flipkart.repo;

import com.shopping.flipkart.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Long> {

    RefreshToken findByToken(String rt);

    List<RefreshToken> findByExpirationBefore(LocalDateTime now);
}
