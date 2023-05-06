package com.project.contactmanagementsystemv4.contacts;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("ALL")
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("cms/api/v1/contacts")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Contact API")
public class ContactController {

    private final ContactService contactService;


    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }


    @Operation(summary = "Create a new contact")
    @PostMapping("")
    public Contact saveContact(@RequestBody Contact contact) {
        return contactService.saveContact(contact);
    }

    @Operation(summary = "Get all contacts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the contacts", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Contacts not found", content = @Content)
    })


    @GetMapping("")
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }


    @Operation(summary = "Get a contact by id")
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable(value = "id") Long contactId) {
        Contact contact = contactService.getContactById(contactId);
        return ResponseEntity.ok().body(contact);
    }

    @Operation(summary = "Delete a contact by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable(value = "id") Long contactId) {
        boolean deleted = false;
        deleted = contactService.deleteContact(contactId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update a contact by id")
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable(value = "id") Long contactId, @RequestBody Contact contactDetails) {
        contactDetails = contactService.updateContact(contactId, contactDetails);
        return ResponseEntity.ok().body(contactDetails);
    }

    //Searching EndPoints
    /*

    @Operation(summary = "Search a contact by firstName")
    @GetMapping("/getContactByFirstName/{firstName}")
    public ResponseEntity<List<Contact>> getContactByFirstName(@PathVariable(value = "firstName") String firstName) {
        List<Contact> contact = contactService.getContactByFirstName(firstName);
        return ResponseEntity.ok().body(contact);
    }

    @Operation(summary = "Search a contact by lastName")
    @GetMapping("/getContactByLastName/{lastName}")
    public ResponseEntity<List<Contact>> getContactByLastName(@PathVariable(value = "lastName") String lastName) {
        List<Contact> contact = contactService.getContactByLastName(lastName);
        return ResponseEntity.ok().body(contact);
    }

    @Operation(summary = "Search a contact by phoneNumber")
    @GetMapping("/getContactByPhoneNumber/{phoneNumber}")
    public ResponseEntity<List<Contact>> getContactByPhoneNumber(@PathVariable(value = "phoneNumber") String phoneNumber) {
        List<Contact> contact = contactService.getContactByPhoneNumber(phoneNumber);
        return ResponseEntity.ok().body(contact);
    }

    @Operation(summary = "Search a contact by email")
    @GetMapping("/getContactByEmail/{email}")
    public ResponseEntity<List<Contact>> getContactByEmail(@PathVariable(value = "email") String email) {
        List<Contact> contact = contactService.getContactByEmail(email);
        return ResponseEntity.ok().body(contact);
    }

    */

    @Operation(summary = "Search a contact by firstName, lastName, phoneNumber, email")
    @GetMapping("/searchContact")
    public ResponseEntity<List<Contact>> searchContact(@RequestParam(value = "firstName", required = false) String firstName,
                                                       @RequestParam(value = "lastName", required = false) String lastName,
                                                       @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                                       @RequestParam(value = "email", required = false) String email) {
        List<Contact> contact = contactService.searchContact(firstName, lastName, phoneNumber, email);
        return ResponseEntity.ok().body(contact);
    }
}
