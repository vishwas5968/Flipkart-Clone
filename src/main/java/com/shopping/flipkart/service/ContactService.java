package com.shopping.flipkart.service;

import com.shopping.flipkart.entity.Contact;
import com.shopping.flipkart.requestdto.ContactRequest;
import com.shopping.flipkart.util.ResponseStructure;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ContactService {

    ResponseEntity<ResponseStructure<Contact>> addContact(ContactRequest contactRequest, int addressId);

    ResponseEntity<ResponseStructure<Contact>> updateContact(ContactRequest contactRequest, int addressId);

    ResponseEntity<ResponseStructure<Contact>> findContactById(int contactId);

    ResponseEntity<ResponseStructure<Contact>> findContactByAddress(int addressId);
}
