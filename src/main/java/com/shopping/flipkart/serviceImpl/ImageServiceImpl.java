package com.shopping.flipkart.serviceImpl;

import com.shopping.flipkart.entity.Image;
import com.shopping.flipkart.entity.StoreImage;
import com.shopping.flipkart.enums.ImageType;
import com.shopping.flipkart.exception.ConstraintViolationException;
import com.shopping.flipkart.repo.ImageRepo;
import com.shopping.flipkart.repo.StoreImageRepo;
import com.shopping.flipkart.repo.StoreRepo;
import com.shopping.flipkart.service.ImageService;
import com.shopping.flipkart.util.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private StoreRepo storeRepo;

    private ImageRepo imageRepo;

    private StoreImageRepo storeImageRepo;

    private ResponseStructure<String> stringResponseStructure;

    @Override
    public ResponseEntity<ResponseStructure<String>> addStoreImage(int storeId, MultipartFile image) {
        StoreImage storeImage = storeRepo.findById(storeId).map(store -> {
//            if ( != 0) {
//                throw new ConstraintViolationException("There is logo present already", HttpStatus.NO_CONTENT.value(), "");
//            }
            StoreImage storeImage1 = new StoreImage();
            storeImage1.setStoreId(storeId);
            storeImage1.setContentType(image.getContentType());
            try {
                storeImage1.setImageBytes(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            storeImage1.setImageType(ImageType.LOGO);
            imageRepo.save(storeImage1);
            return storeImage1;
        }).orElseThrow(() -> {
            throw new ConstraintViolationException("Store not found", HttpStatus.NO_CONTENT.value(), "");
        });
        stringResponseStructure.setStatus(HttpStatus.OK.value());
        stringResponseStructure.setMessage("Logo added");
        stringResponseStructure.setData("/images/"+storeImage.getImageId());
        return new ResponseEntity<>(stringResponseStructure,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getStoreImage(String imageId) {
        Image image = imageRepo.findById(imageId).orElseThrow(() -> {
            throw new ConstraintViolationException("Store not found", HttpStatus.NO_CONTENT.value(), "");
        });
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getContentType())).contentLength(image.getImageBytes().length).body(image.getImageBytes());
    }

}
