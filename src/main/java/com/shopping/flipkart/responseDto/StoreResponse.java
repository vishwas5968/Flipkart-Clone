package com.shopping.flipkart.responseDto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreResponse {

    private int storeId;
    private String storeName;
    private String logoLink;
    private String about;

}
