package com.shopping.flipkart.repo;

import com.shopping.flipkart.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepo extends JpaRepository<AccessToken,Long> {

}
