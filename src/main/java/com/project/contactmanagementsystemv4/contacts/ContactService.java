package com.project.contactmanagementsystemv4.contacts;

import java.util.List;

public interface ContactService {
    Contact saveContact(Contact contact);
    List<Contact> getAllContacts();
    Contact getContactById(long id);
    boolean deleteContact(long id);

    Contact updateContact(Long id, Contact contact);

    List<Contact> getContactByFirstName(String firstName);

    List<Contact> getContactByLastName(String lastName);

    List<Contact> getContactByPhoneNumber(String phoneNumber);

    List<Contact> getContactByEmail(String email);
    List<Contact> searchContact(String firstName, String lastName, String phoneNumber, String email);


}
