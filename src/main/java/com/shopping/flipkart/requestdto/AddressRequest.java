package com.shopping.flipkart.requestdto;

import com.shopping.flipkart.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressRequest {
    private String streetAddress;
    private String streetAddressAdditional;
    private String country;
    private String state;
    private String city;
    private int pincode;
    private AddressType addressType;
}
