package com.shopping.flipkart.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.Duration;


public class CacheStore <T>{
    private Cache<String,T> cache;

    public CacheStore(Duration expiry) {
        this.cache = CacheBuilder.newBuilder().expireAfterWrite(expiry).concurrencyLevel(Runtime.getRuntime().availableProcessors()).build();
    }

    public void setCache(String key,T value) {
        cache.put(key, value);
    }

    public T getCache(String key) {
        return cache.getIfPresent(key);
    }

    public  void remove(String key){
        cache.invalidate(key);
    }
}
