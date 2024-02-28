package com.shopping.flipkart.serviceImpl;

import com.shopping.flipkart.entity.Seller;
import com.shopping.flipkart.entity.Store;
import com.shopping.flipkart.exception.ConstraintViolationException;
import com.shopping.flipkart.repo.SellerRepo;
import com.shopping.flipkart.repo.StoreRepo;
import com.shopping.flipkart.repo.UserRepo;
import com.shopping.flipkart.requestdto.StoreRequest;
import com.shopping.flipkart.responseDto.StoreResponse;
import com.shopping.flipkart.service.StoreService;
import com.shopping.flipkart.util.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService {

    private StoreRepo storeRepo;

    private ResponseStructure<StoreResponse> storeResponseStructure;

    private UserRepo userRepo;

    private SellerRepo sellerRepo;

    @Override
    public ResponseEntity<ResponseStructure<StoreResponse>> createStore(StoreRequest storeRequest, int sellerId) {
        Seller seller = sellerRepo.findById(sellerId).get();
        if (seller.getStore() != null) {
            throw new ConstraintViolationException("User with store already present", HttpStatus.ALREADY_REPORTED.value(), "No user can have more than 1 store");
        }
        Store store=mapToStore(storeRequest);
        seller.setStore(store);
        storeRepo.save(store);
        sellerRepo.save(seller);
        storeResponseStructure.setStatus(HttpStatus.CREATED.value());
        storeResponseStructure.setData(mapToStoreResponse(store));
        storeResponseStructure.setMessage("Store successfully created");
        return new ResponseEntity<>(storeResponseStructure,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<StoreResponse>> fetchStore(int storeId) {
        Store store = storeRepo.findById(storeId).orElseThrow(() -> {
            throw new ConstraintViolationException("Store not found", HttpStatus.NO_CONTENT.value(), "");
        });
        storeResponseStructure.setStatus(HttpStatus.OK.value());
        storeResponseStructure.setData(mapToStoreResponse(store));
        storeResponseStructure.setMessage("Store successfully fetched");
        return new ResponseEntity<>(storeResponseStructure,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<StoreResponse>> fetchStoreBySeller(int sellerId) {
        Seller seller = sellerRepo.findById(sellerId).orElseThrow(() -> {
            throw new ConstraintViolationException("User not found", HttpStatus.NO_CONTENT.value(), "");
        });
        storeResponseStructure.setStatus(HttpStatus.OK.value());
        storeResponseStructure.setData(mapToStoreResponse(seller.getStore()));
        storeResponseStructure.setMessage("Store successfully fetched");
        return new ResponseEntity<>(storeResponseStructure,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<StoreResponse>> updateStore(StoreRequest storeRequest, int storeId) {
        storeRepo.findById(storeId).ifPresent(store -> {
            store.setStoreName(storeRequest.getStoreName());
            store.setAbout(storeRequest.getAbout());
            storeRepo.save(store);
            storeResponseStructure.setStatus(HttpStatus.OK.value());
            storeResponseStructure.setData(mapToStoreResponse(store));
            storeResponseStructure.setMessage("Store successfully fetched");
        });
        return new ResponseEntity<>(storeResponseStructure,HttpStatus.OK);
    }


    public Store mapToStore(StoreRequest storeRequest){
        return Store.builder().storeName(storeRequest.getStoreName()).about(storeRequest.getAbout()).build();
    }
    public StoreResponse mapToStoreResponse(Store store){
        return StoreResponse.builder().storeId(store.getStoreId()).storeName(store.getStoreName()).about(store.getAbout()).logoLink(store.getLogoLink()).build();
    }
}
