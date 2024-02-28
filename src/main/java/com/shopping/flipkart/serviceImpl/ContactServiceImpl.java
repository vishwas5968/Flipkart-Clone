package com.shopping.flipkart.serviceImpl;

import com.shopping.flipkart.entity.Address;
import com.shopping.flipkart.entity.Contact;
import com.shopping.flipkart.exception.ConstraintViolationException;
import com.shopping.flipkart.repo.AddressRepo;
import com.shopping.flipkart.repo.ContactRepo;
import com.shopping.flipkart.requestdto.ContactRequest;
import com.shopping.flipkart.service.ContactService;
import com.shopping.flipkart.util.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {

    private AddressRepo addressRepo;

    private ContactRepo contactRepo;

    private ResponseStructure<Contact> contactResponseStructure;
    private ResponseStructure<List<Contact>> contactsResponseStructure;

    @Override
    public ResponseEntity<ResponseStructure<Contact>> addContact(ContactRequest contactRequest, int addressId) {
        Address address = addressRepo.findById(addressId).orElseThrow(() -> {
            throw new ConstraintViolationException("Address not found", HttpStatus.NO_CONTENT.value(), "");
        });
        if (address.getContacts().size()==2)
            throw new ConstraintViolationException("Address with contacts already present", HttpStatus.ALREADY_REPORTED.value(), "No Address can have more than 2 contacts");
        Contact contact=mapToContact(contactRequest);
        contact.setAddress(address);
        Contact contact1 = contactRepo.save(contact);
        contactResponseStructure.setStatus(HttpStatus.CREATED.value());
        contactResponseStructure.setData(contact1);
        contactResponseStructure.setMessage("Contact successfully created");
        return new ResponseEntity<>(contactResponseStructure,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<Contact>> updateContact(ContactRequest contactRequest, int addressId) {
        Address address = addressRepo.findById(addressId).orElseThrow(() -> {
            throw new ConstraintViolationException("Address not found", HttpStatus.NO_CONTENT.value(), "");
        });
        List<Contact> contacts = address.getContacts();
        Contact contact=null;
        for (Contact contact1:contacts){
            if (contact1.getName().equals(contactRequest.getName()))
                contact=contact1;
        }
        contact.setContactNumber(contactRequest.getContactNumber());
        contact.setName(contactRequest.getName());
        contact.setPriority(contactRequest.getPriority());
        contactRepo.save(contact);
        contactResponseStructure.setStatus(HttpStatus.CREATED.value());
        contactResponseStructure.setData(mapToContact(contactRequest));
        contactResponseStructure.setMessage("Contact successfully created");
        return new ResponseEntity<>(contactResponseStructure,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<Contact>> findContactById(int contactId) {
        Contact contact = contactRepo.findById(contactId).orElseThrow(() -> {
            throw new ConstraintViolationException("Contact not found", HttpStatus.NO_CONTENT.value(), "");
        });
        contactResponseStructure.setStatus(HttpStatus.CREATED.value());
        contactResponseStructure.setData(contact);
        contactResponseStructure.setMessage("Contact successfully found");
        return null;
    }

    @Override
    public ResponseEntity<ResponseStructure<Contact>> findContactByAddress(int addressId) {
        Address address = addressRepo.findById(addressId).orElseThrow(() -> {
            throw new ConstraintViolationException("Address not found", HttpStatus.NO_CONTENT.value(), "");
        });
        List<Contact> contacts = address.getContacts();
        contactsResponseStructure.setStatus(HttpStatus.CREATED.value());
        contactsResponseStructure.setData(contacts);
        contactsResponseStructure.setMessage("Contacts successfully found");
        return null;
    }


    public Contact mapToContact(ContactRequest contactRequest){
        return Contact.builder().contactNumber(contactRequest.getContactNumber()).name(contactRequest.getName()).priority(contactRequest.getPriority()).build();
    }
}
