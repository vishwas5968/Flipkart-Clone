package com.shopping.flipkart.controller;

import com.shopping.flipkart.service.ImageService;
import com.shopping.flipkart.util.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:5173/")
@AllArgsConstructor
public class ImageController {

    private ImageService imageService;

    @PostMapping("/stores/{storeId}/images")
    public ResponseEntity<ResponseStructure<String>> addStoreImage(@PathVariable int storeId, MultipartFile image){
        return imageService.addStoreImage(storeId,image);
    }

    @GetMapping("images/{imageId}")
    public ResponseEntity<byte[]> getStoreImage(@PathVariable String imageId){
        return imageService.getStoreImage(imageId);
    }
}
