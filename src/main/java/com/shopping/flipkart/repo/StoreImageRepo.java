package com.shopping.flipkart.repo;

import com.shopping.flipkart.entity.StoreImage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoreImageRepo extends MongoRepository<StoreImage,Integer> {
}
