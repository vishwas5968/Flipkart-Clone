package com.shopping.flipkart.serviceImpl;

import com.shopping.flipkart.entity.Address;
import com.shopping.flipkart.entity.Store;
import com.shopping.flipkart.exception.ConstraintViolationException;
import com.shopping.flipkart.repo.AddressRepo;
import com.shopping.flipkart.repo.StoreRepo;
import com.shopping.flipkart.requestdto.AddressRequest;
import com.shopping.flipkart.responseDto.AddressResponse;
import com.shopping.flipkart.service.AddressService;
import com.shopping.flipkart.util.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private AddressRepo addressRepo;

    private StoreRepo storeRepo;

    private ResponseStructure<AddressResponse> addressResponseStructure;

    @Override
    public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest, int storeId) {
        Store store = storeRepo.findById(storeId).orElseThrow(() -> {
            throw new ConstraintViolationException("Store not found", HttpStatus.NO_CONTENT.value(), "");
        });
        Address address = mapToAddress(addressRequest);
        store.setAddress(address);
        addressRepo.save(address);
        storeRepo.save(store);
        addressResponseStructure.setStatus(HttpStatus.CREATED.value());
        addressResponseStructure.setData(mapToAddressResponse(address));
        addressResponseStructure.setMessage("Address successfully created");
        return new ResponseEntity<>(addressResponseStructure, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(AddressRequest addressRequest, int addressId) {
        Address address = addressRepo.findById(addressId).orElseThrow(() -> {
            throw new ConstraintViolationException("Address not found", HttpStatus.NO_CONTENT.value(), "");
        });
        address.setAddressType(addressRequest.getAddressType());
        address.setStreetAddress(addressRequest.getStreetAddress());
        address.setCity(addressRequest.getCity());
        address.setCountry(addressRequest.getCountry());
        address.setState(addressRequest.getState());
        address.setPincode(addressRequest.getPincode());
        address.setStreetAddressAdditional(addressRequest.getStreetAddressAdditional());
        addressRepo.save(address);
        addressResponseStructure.setStatus(HttpStatus.CREATED.value());
        addressResponseStructure.setData(mapToAddressResponse(address));
        addressResponseStructure.setMessage("Address successfully Updated");
        return new ResponseEntity<>(addressResponseStructure, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<AddressResponse>> findAddressById(int addressId) {
        Address address = addressRepo.findById(addressId).orElseThrow(() -> {
            throw new ConstraintViolationException("Address not found", HttpStatus.NO_CONTENT.value(), "");
        });
        addressResponseStructure.setStatus(HttpStatus.FOUND.value());
        addressResponseStructure.setData(mapToAddressResponse(address));
        addressResponseStructure.setMessage("Address successfully Updated");
        return new ResponseEntity<>(addressResponseStructure, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ResponseStructure<AddressResponse>> findAddressByStore(int storeId) {
        Store store = storeRepo.findById(storeId).orElseThrow(() -> {
            throw new ConstraintViolationException("Store not found", HttpStatus.NO_CONTENT.value(), "");
        });
        return null;
    }

    public Address mapToAddress(AddressRequest addressRequest) {
        return Address.builder().addressType(addressRequest.getAddressType()).streetAddress(addressRequest.getStreetAddress()).streetAddressAdditional(addressRequest.getStreetAddressAdditional()).city(addressRequest.getCity()).country(addressRequest.getCountry()).pincode(addressRequest.getPincode()).state(addressRequest.getState()).build();
    }

    public AddressResponse mapToAddressResponse(Address address) {
        return AddressResponse.builder().addressId(address.getAddressId()).addressType(address.getAddressType()).streetAddress(address.getStreetAddress()).streetAddressAdditional(address.getStreetAddressAdditional()).city(address.getCity()).country(address.getCountry()).pincode(address.getPincode()).state(address.getState()).build();
    }

}
