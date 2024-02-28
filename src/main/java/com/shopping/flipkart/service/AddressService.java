package com.shopping.flipkart.service;

import com.shopping.flipkart.requestdto.AddressRequest;
import com.shopping.flipkart.responseDto.AddressResponse;
import com.shopping.flipkart.util.ResponseStructure;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest, int storeId);

    ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(AddressRequest addressRequest, int addressId);

    ResponseEntity<ResponseStructure<AddressResponse>> findAddressById(int addressId);

    ResponseEntity<ResponseStructure<AddressResponse>> findAddressByStore(int storeId);
}
