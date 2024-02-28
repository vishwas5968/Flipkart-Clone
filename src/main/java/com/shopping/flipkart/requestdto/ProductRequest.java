package com.shopping.flipkart.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductRequest {

    private String productName;
    private String productDescription;
    private double productPrice;
    private int productQuantity;

}