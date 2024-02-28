package com.shopping.flipkart.repo;

import com.shopping.flipkart.entity.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepo extends MongoRepository<Image,String> {

}
