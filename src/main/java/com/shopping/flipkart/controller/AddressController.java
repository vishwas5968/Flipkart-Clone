package com.shopping.flipkart.controller;

import com.shopping.flipkart.requestdto.AddressRequest;
import com.shopping.flipkart.responseDto.AddressResponse;
import com.shopping.flipkart.service.AddressService;
import com.shopping.flipkart.util.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(allowCredentials = "true",origins = "http://localhost:5173/")
//@RequestMapping("/api/v1")
@AllArgsConstructor
public class AddressController {

    private AddressService addressService;

    @PostMapping("addresses/{storeId}/stores")
    public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(@RequestBody AddressRequest addressRequest,@PathVariable int storeId){
        return addressService.addAddress(addressRequest,storeId);
    }

    @PostMapping("addresses/{addressId}")
    public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(@RequestBody AddressRequest addressRequest,@PathVariable int addressId){
        return addressService.updateAddress(addressRequest,addressId);
    }

    @GetMapping("addresses/{addressId}")
    public ResponseEntity<ResponseStructure<AddressResponse>> findAddressById(@PathVariable int addressId){
        return addressService.findAddressById(addressId);
    }

    @GetMapping("addresses/{storeId}/stores")
    public ResponseEntity<ResponseStructure<AddressResponse>> findAddressByStore(@PathVariable int storeId){
        return addressService.findAddressByStore(storeId);
    }

}
