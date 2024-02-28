package com.shopping.flipkart.controller;

import com.shopping.flipkart.entity.Product;
import com.shopping.flipkart.requestdto.ProductRequest;
import com.shopping.flipkart.service.ProductService;
import com.shopping.flipkart.util.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@CrossOrigin(allowCredentials = "true", origins = "http://localhost:5173/")
public class ProductController
{
    ProductService service;

    //	@PreAuthorize(value = "hasAuthority('SELLER')")
    @PostMapping(path = "/products/{sellerId}")
    public ResponseEntity<ResponseStructure<Product>> createProduct(@RequestBody ProductRequest productRequest, @PathVariable int sellerId)
    {
        return service.createProduct(productRequest, sellerId);
    }
}