package com.shopping.flipkart.util;

import com.shopping.flipkart.entity.AccessToken;
import com.shopping.flipkart.entity.RefreshToken;
import com.shopping.flipkart.repo.AccessTokenRepo;
import com.shopping.flipkart.repo.RefreshTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScheduledJobs {
    @Autowired
    private AccessTokenRepo accessTokenRepo;
    @Autowired
    private RefreshTokenRepo refreshTokenRepo;
    @Scheduled(cron = "")
    public void removeExpiredRefreshToken(){
        List<RefreshToken> refreshTokenList = refreshTokenRepo.findByExpirationBefore(LocalDateTime.now());
        refreshTokenRepo.deleteAll(refreshTokenList);
    }

    @Scheduled(cron = "")
    public void removeExpiredAccessToken(){
        List<AccessToken> accessTokenList = accessTokenRepo.findByExpirationBefore(LocalDateTime.now());
        accessTokenRepo.deleteAll(accessTokenList);
    }

}
