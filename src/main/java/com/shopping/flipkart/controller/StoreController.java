package com.shopping.flipkart.controller;

import com.shopping.flipkart.requestdto.StoreRequest;
import com.shopping.flipkart.responseDto.StoreResponse;
import com.shopping.flipkart.service.StoreService;
import com.shopping.flipkart.util.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:5173/")
public class StoreController {

    private StoreService storeService;

    @PostMapping("/stores/{sellerId}")
    public ResponseEntity<ResponseStructure<StoreResponse>> createStore(@RequestBody StoreRequest storeRequest, @PathVariable int sellerId){
        return storeService.createStore(storeRequest,sellerId);
    }

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<ResponseStructure<StoreResponse>> fetchStoreByStoreId(@PathVariable int storeId){
        return storeService.fetchStore(storeId);
    }

    @GetMapping("/sellers/{sellerId}/stores")
    public ResponseEntity<ResponseStructure<StoreResponse>> fetchStoreBySeller(@PathVariable int sellerId){
        return storeService.fetchStoreBySeller(sellerId);
    }

    @PutMapping("/stores/{storeId}")
    public ResponseEntity<ResponseStructure<StoreResponse>> updateStore(@RequestBody StoreRequest storeRequest,@PathVariable int storeId){
        return storeService.updateStore(storeRequest,storeId);
    }
}
