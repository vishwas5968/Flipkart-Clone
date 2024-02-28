package com.shopping.flipkart.serviceImpl;

import com.shopping.flipkart.entity.Product;
import com.shopping.flipkart.entity.Seller;
import com.shopping.flipkart.exception.ConstraintViolationException;
import com.shopping.flipkart.repo.ProductRepo;
import com.shopping.flipkart.repo.SellerRepo;
import com.shopping.flipkart.requestdto.ProductRequest;
import com.shopping.flipkart.service.ProductService;
import com.shopping.flipkart.util.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService
{
    private ProductRepo productsRepo;
    private SellerRepo sellerRepo;
    private ResponseStructure<Product> productStructure;

    @Override
    public ResponseEntity<ResponseStructure<Product>> createProduct(ProductRequest productRequest, int sellerId)
    {
        Seller seller = sellerRepo.findById(sellerId).orElseThrow(() -> new ConstraintViolationException("Seller not present !!!",HttpStatus.BAD_GATEWAY.value(), ""));
        Product product = mapToProduct(productRequest, seller);
        productsRepo.save(product);
        productStructure.setData(product);
        productStructure.setMessage("Product Added !!!");
        productStructure.setStatus(HttpStatus.CREATED.value());

        return new ResponseEntity<>(productStructure, HttpStatus.OK);
    }

    private Product mapToProduct(ProductRequest productRequest, Seller seller) {
        return Product.builder()
                .productName(productRequest.getProductName())
                .productDescription(productRequest.getProductDescription())
                .productPrice(productRequest.getProductPrice())
                .productQuantity(productRequest.getProductQuantity())
                .sellerId(seller.getUserId())
                .storeId(seller.getStore().getStoreId())
                .build();
    }

}