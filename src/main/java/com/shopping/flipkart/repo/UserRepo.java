package com.shopping.flipkart.repo;

import com.shopping.flipkart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String email);
}
