package com.shopping.flipkart.responseDto;

import com.shopping.flipkart.enums.AddressType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressResponse {

    private int addressId;
    private String streetAddress;
    private String streetAddressAdditional;
    private String country;
    private String state;
    private String city;
    private int pincode;
    private AddressType addressType;

}
