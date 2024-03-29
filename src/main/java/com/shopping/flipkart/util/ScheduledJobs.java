package com.shopping.flipkart.util;

import com.shopping.flipkart.entity.AccessToken;
import com.shopping.flipkart.entity.RefreshToken;
import com.shopping.flipkart.repo.AccessTokenRepo;
import com.shopping.flipkart.repo.RefreshTokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class ScheduledJobs {

    private AccessTokenRepo accessTokenRepo;
    private RefreshTokenRepo refreshTokenRepo;

    @Scheduled(cron = "0 */6 * * *")
    public void removeExpiredRefreshToken(){
        List<RefreshToken> refreshTokenList = refreshTokenRepo.findByExpirationBefore(LocalDateTime.now());
        refreshTokenRepo.deleteAll(refreshTokenList);
    }

    @Scheduled(cron = "0 */6 * * *")
    public void removeExpiredAccessToken(){
        List<AccessToken> accessTokenList = accessTokenRepo.findByExpirationBefore(LocalDateTime.now());
        accessTokenRepo.deleteAll(accessTokenList);
    }

}
