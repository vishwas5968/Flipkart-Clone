package com.shopping.flipkart.repo;

import com.shopping.flipkart.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Long> {

}
