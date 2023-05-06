package com.project.contactmanagementsystemv4.contacts;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImplementation implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImplementation(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact saveContact(Contact contact) {
        ContactEntity contactEntity = new ContactEntity();
        BeanUtils.copyProperties(contact, contactEntity);
        ContactEntity savedContactEntity = contactRepository.save(contactEntity);
        return contact;
    }

    @Override
    public List<Contact> getAllContacts() {
        // TODO implement here

        List<ContactEntity> contactEntities = contactRepository.findAll();

        List<Contact> contacts = contactEntities
                .stream()
                .map(contactEntity -> new Contact(
                        contactEntity.getId(),
                        contactEntity.getFirstName(),
                        contactEntity.getLastName(),
                        contactEntity.getPhoneNumber(),
                        contactEntity.getEmail()
                )).collect(Collectors.toList());
        return contacts;
    }

    @Override
    public Contact getContactById(long id) {

        ContactEntity contactEntity = contactRepository.findById(id).get();
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactEntity, contact);
        return contact;
    }

    @Override
    public boolean deleteContact(long id) {
        ContactEntity contactEntity = contactRepository.findById(id).get();
        contactRepository.delete(contactEntity);
        return false;
    }

    public Contact updateContact(Long id, Contact contact) {
        ContactEntity contactEntity = contactRepository.findById(id).get();
        contactEntity.setFirstName(contact.getFirstName());
        contactEntity.setLastName(contact.getLastName());
        contactEntity.setPhoneNumber(contact.getPhoneNumber());
        contactEntity.setEmail(contact.getEmail());
        contactRepository.save(contactEntity);
        return contact;
    }

    //Searching implementations are done here
    @Override
    public List<Contact> getContactByFirstName(String firstName) {
        List<ContactEntity> contacts = contactRepository.findAll();

        List<ContactEntity> result = new ArrayList<>();

        for(ContactEntity contact : contacts){
            if(firstName == null || contact.getFirstName().equalsIgnoreCase(firstName)){
                result.add(contact);
            }
        }

        List<Contact> contactList = result.stream()
                .map(contactEntity -> new Contact(
                        contactEntity.getId(),
                        contactEntity.getFirstName(),
                        contactEntity.getLastName(),
                        contactEntity.getPhoneNumber(),
                        contactEntity.getEmail()
                )).collect(Collectors.toList());

        return contactList;
    }

    @Override
    public List<Contact> getContactByLastName(String lastName) {
        List<ContactEntity> contacts = contactRepository.findAll();
        List<ContactEntity> result = new ArrayList<>();
        for(ContactEntity contact : contacts){
            if(lastName == null || contact.getFirstName().equalsIgnoreCase(lastName)){
                result.add(contact);
            }
        }
        List<Contact> contactList = result.stream()
                .map(contactEntity -> new Contact(
                        contactEntity.getId(),
                        contactEntity.getFirstName(),
                        contactEntity.getLastName(),
                        contactEntity.getPhoneNumber(),
                        contactEntity.getEmail()
                )).collect(Collectors.toList());
        return contactList;
    }

    @Override
    public List<Contact> getContactByPhoneNumber(String phoneNumber) {
        List<ContactEntity> contacts = contactRepository.findAll();
        List<ContactEntity> result = new ArrayList<>();
        for(ContactEntity contact : contacts){
            if(phoneNumber == null || contact.getFirstName().equalsIgnoreCase(phoneNumber)){
                result.add(contact);
            }
        }
        List<Contact> contactList = result.stream()
                .map(contactEntity -> new Contact(
                        contactEntity.getId(),
                        contactEntity.getFirstName(),
                        contactEntity.getLastName(),
                        contactEntity.getPhoneNumber(),
                        contactEntity.getEmail()
                )).collect(Collectors.toList());
        return contactList;
    }

    @Override
    public List<Contact> getContactByEmail(String email) {
        List<ContactEntity> contacts = contactRepository.findAll();
        List<ContactEntity> result = new ArrayList<>();
        for(ContactEntity contact : contacts){
            if(email == null || contact.getFirstName().equalsIgnoreCase(email)){
                result.add(contact);
            }
        }
        List<Contact> contactList = result.stream()
                .map(contactEntity -> new Contact(
                        contactEntity.getId(),
                        contactEntity.getFirstName(),
                        contactEntity.getLastName(),
                        contactEntity.getPhoneNumber(),
                        contactEntity.getEmail()
                )).collect(Collectors.toList());
        return contactList;
    }

    @Override
    public List<Contact> searchContact(String firstName, String lastName, String phoneNumber, String email) {
        List<ContactEntity> contacts = contactRepository.findAll();
        List<ContactEntity> result = new ArrayList<>();

        for(ContactEntity contact : contacts){
            if((firstName == null || contact.getFirstName().equalsIgnoreCase(firstName))
                    && (lastName == null || contact.getLastName().equalsIgnoreCase(lastName))
                    && (phoneNumber == null || contact.getPhoneNumber().equalsIgnoreCase(phoneNumber))
                    && (email == null || contact.getEmail().equalsIgnoreCase(email))){
                result.add(contact);
            }
        }

        List<Contact> contactList = result.stream()
                .map(contactEntity -> new Contact(
                        contactEntity.getId(),
                        contactEntity.getFirstName(),
                        contactEntity.getLastName(),
                        contactEntity.getEmail(),
                        contactEntity.getPhoneNumber()
                )).collect(Collectors.toList());

        return contactList;
    }
}
