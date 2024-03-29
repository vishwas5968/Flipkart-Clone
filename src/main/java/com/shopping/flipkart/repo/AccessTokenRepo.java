package com.shopping.flipkart.repo;

import com.shopping.flipkart.entity.AccessToken;
import com.shopping.flipkart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccessTokenRepo extends JpaRepository<AccessToken,Long> {

    Optional<AccessToken> findByToken(String at);

    List<AccessToken> findByExpirationBefore(LocalDateTime now);

    Optional<AccessToken> findByTokenAndIsBlocked(String at, boolean b);

    List<AccessToken> findByUserAndIsBlocked(User user,boolean b);

    List<AccessToken> findAllByUserAndIsBlockedAndTokenNot(User user, boolean b, String accessToken);

    boolean existsByTokenAndIsBlocked(String at, boolean b);
}
