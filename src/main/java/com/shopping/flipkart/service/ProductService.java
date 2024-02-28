package com.shopping.flipkart.service;

import com.shopping.flipkart.entity.Product;
import com.shopping.flipkart.requestdto.ProductRequest;
import com.shopping.flipkart.util.ResponseStructure;
import org.springframework.http.ResponseEntity;

public interface ProductService {

    ResponseEntity<ResponseStructure<Product>> createProduct(ProductRequest productRequest, int sellerId);

}
