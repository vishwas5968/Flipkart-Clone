package com.shopping.flipkart.service;

import com.shopping.flipkart.util.ResponseStructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
public interface ImageService {
    ResponseEntity<ResponseStructure<String>> addStoreImage(int storeId, MultipartFile image);

    ResponseEntity<byte[]> getStoreImage(String imageId);
}
