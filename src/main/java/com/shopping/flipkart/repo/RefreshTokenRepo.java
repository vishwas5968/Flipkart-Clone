package com.shopping.flipkart.repo;

import com.shopping.flipkart.entity.RefreshToken;
import com.shopping.flipkart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByToken(String rt);

    List<RefreshToken> findByExpirationBefore(LocalDateTime now);

    Optional<RefreshToken> findByTokenAndIsBlocked(User user, boolean b);

    List<RefreshToken> findAllByUserAndIsBlockedAndTokenNot(User user, boolean b, String refreshToken);

    boolean existsByTokenAndIsBlocked(String rt, boolean b);

    List<RefreshToken> findByUserAndIsBlocked(User user, boolean b);
}
