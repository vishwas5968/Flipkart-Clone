package com.shopping.flipkart.repo;

import com.shopping.flipkart.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepo extends JpaRepository<Store,Integer> {

    boolean existsByStoreName(String storeName);

}
