package com.shopping.flipkart.repo;

import com.shopping.flipkart.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<Product,String> {

}
