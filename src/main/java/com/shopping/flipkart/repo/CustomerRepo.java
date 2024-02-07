package com.shopping.flipkart.repo;

import com.shopping.flipkart.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {

}
