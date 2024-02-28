package com.shopping.flipkart.service;

import com.shopping.flipkart.requestdto.StoreRequest;
import com.shopping.flipkart.responseDto.StoreResponse;
import com.shopping.flipkart.util.ResponseStructure;
import org.springframework.http.ResponseEntity;

public interface StoreService {
    ResponseEntity<ResponseStructure<StoreResponse>> createStore(StoreRequest storeRequest, int sellerId);

    ResponseEntity<ResponseStructure<StoreResponse>> fetchStore(int storeId);

    ResponseEntity<ResponseStructure<StoreResponse>> updateStore(StoreRequest storeRequest, int storeId);

    ResponseEntity<ResponseStructure<StoreResponse>> fetchStoreBySeller(int sellerId);
}
