package com.shopping.flipkart.controller;

import com.shopping.flipkart.entity.Contact;
import com.shopping.flipkart.requestdto.ContactRequest;
import com.shopping.flipkart.service.ContactService;
import com.shopping.flipkart.util.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:5173/")
@AllArgsConstructor
public class ContactController {

    private ContactService contactService;

    @PostMapping("addresses/{addressId}/contacts")
    public ResponseEntity<ResponseStructure<Contact>> addContact(@RequestBody ContactRequest contactRequest,@PathVariable int addressId){
        return contactService.addContact(contactRequest,addressId);
    }

    @PutMapping("addresses/{addressId}/contacts")
    public ResponseEntity<ResponseStructure<Contact>> updateContact(@RequestBody ContactRequest contactRequest,@PathVariable int addressId){
        return contactService.updateContact(contactRequest,addressId);
    }

    @GetMapping("contacts/{contactId}")
    public ResponseEntity<ResponseStructure<Contact>> findContactById(@RequestBody ContactRequest contactRequest,@PathVariable int contactId){
        return contactService.findContactById(contactId);
    }

    @GetMapping("addresses/{addressId}/contacts")
    public ResponseEntity<ResponseStructure<Contact>> findContactByAddress(@RequestBody ContactRequest contactRequest,@PathVariable int addressId){
        return contactService.findContactByAddress(addressId);
    }

}
