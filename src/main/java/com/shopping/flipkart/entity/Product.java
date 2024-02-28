package com.shopping.flipkart.entity;

import com.shopping.flipkart.enums.ProductAvailability;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Map;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "products")
public class Product {

    @MongoId
    private String productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private int productQuantity;
    private ProductAvailability productAvailability;
    private double averageRating;
    private int totalOrders;
    private int storeId;
    private int sellerId;
    private Map<String,String> features;

}
