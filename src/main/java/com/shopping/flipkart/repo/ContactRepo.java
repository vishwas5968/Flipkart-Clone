package com.shopping.flipkart.repo;

import com.shopping.flipkart.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepo extends JpaRepository<Contact,Integer> {

}
