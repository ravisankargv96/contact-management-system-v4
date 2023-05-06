package com.project.contactmanagementsystemv4.contacts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class) //This annotation tells JUnit to run using Spring’s testing support.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RequestMapping("cms/api/v1")
public class ContactControllerTests {

    private final String path = "/cms/api/v1/contacts";
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContactRepository contactRepository;

    @Before
    public void setup() {
        Contact contact1 = new Contact(1L,"John", "Doe", "johndoe@example.com", "1234567890");
        Contact contact2 = new Contact(2L,"Jane", "Doe", "janedoe@example.com", "0987654321");
        Contact contact3 = new Contact(3L,"Bob", "Smith", "bobsmith@example.com", "5555555555");
        List<Contact> contacts = Arrays.asList(contact1, contact2, contact3);

        List<ContactEntity> contactEntities = new ArrayList<>();
        for(Contact contact:contacts){
            ContactEntity contactEntity = new ContactEntity();
            contactEntity.setId(contact.getId());
            contactEntity.setFirstName(contact.getFirstName());
            contactEntity.setLastName(contact.getLastName());
            contactEntity.setEmail(contact.getEmail());
            contactEntity.setPhoneNumber(contact.getPhoneNumber());
            contactEntities.add(contactEntity);
        }
        contactRepository.saveAll(contactEntities);
    }


    @After
    public void cleanup() {
        //contactRepository.deleteAll();
    }

    @Test
    public void createContact_createsNewContact() throws Exception {

        Contact contact = new Contact(50L,"Alice", "Johnson", "alicejohnson@example.com", "9999999999");
        String jsonRequest = new ObjectMapper().writeValueAsString(contact);
        mockMvc.perform(post(path + "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Alice")))
                .andExpect(jsonPath("$.lastName", is("Johnson")))
                .andExpect(jsonPath("$.email", is("alicejohnson@example.com")))
                .andExpect(jsonPath("$.phoneNumber", is("9999999999")));

    }

    @Test
    public void readContact_returnsExistingContact() throws Exception {

        ContactEntity contactEntity = contactRepository.findAll().get(0);

        mockMvc.perform(get(path + "/" + contactEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(contactEntity.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(contactEntity.getLastName())))
                .andExpect(jsonPath("$.email", is(contactEntity.getEmail())))
                .andExpect(jsonPath("$.phoneNumber", is(contactEntity.getPhoneNumber())));
    }

    @Test
    public void updateContact_updatesExistingContact() throws Exception {
        setup();
        ContactEntity contactEntity = contactRepository.findAll().get(0);
        String updatedLastName = "Updated";
        contactEntity.setLastName(updatedLastName);
        String jsonRequest = new ObjectMapper().writeValueAsString(contactEntity);
        mockMvc.perform(put(path + "/" + contactEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(contactEntity.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedLastName)))
                .andExpect(jsonPath("$.email", is(contactEntity.getEmail())))
                .andExpect(jsonPath("$.phoneNumber", is(contactEntity.getPhoneNumber())));
    }

    @Test
    public void deleteContact_deletesExistingContact() throws Exception {
        ContactEntity contactEntity = contactRepository.findAll().get(0);
        mockMvc.perform(delete(path + "/" + contactEntity.getId()))
                .andExpect(status().isOk());
        Optional<ContactEntity> deletedContact = contactRepository.findById(contactEntity.getId());
        assertThat(deletedContact.isPresent(), is(false));
    }


    //TestCases for SearchAPIs

    @Test
    public void searchContacts_returnsAllContacts_whenNoSearchParamsProvided() throws Exception {

        List<ContactEntity> contactEntities = contactRepository.findAll();
        mockMvc.perform(get(path + "/searchContact"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is(contactEntities.get(0).getFirstName())))
                .andExpect(jsonPath("$[1].firstName", is(contactEntities.get(1).getFirstName())))
                .andExpect(jsonPath("$[2].firstName", is(contactEntities.get(2).getFirstName())));
    }

    @Test
    public void searchContacts_returnsMatchingContacts_whenFirstNameIsProvided() throws Exception {
        mockMvc.perform(get(path + "/searchContact")
                        .param("firstName", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("John")));
    }

    @Test
    public void searchContacts_returnsMatchingContacts_whenLastNameIsProvided() throws Exception {
        mockMvc.perform(get(path + "/searchContact")
                        .param("lastName", "Doe"))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].lastName", is("Doe")))
                .andExpect(jsonPath("$[1].lastName", is("Doe")));
    }

    @Test
    public void searchContacts_returnsMatchingContacts_whenEmailIsProvided() throws Exception {
        mockMvc.perform(get(path + "/searchContact")
                        .param("email", "bobsmith@example.com"))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is("bobsmith@example.com")));
    }

    @Test
    public void searchContacts_returnsEmptyList_whenNoMatchesFound() throws Exception {
        mockMvc.perform(get(path + "/searchContact")
                        .param("firstName", "Nonexistent")
                        .param("lastName", "Contact")
                        .param("email", "invalid@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }


    //Testcases for getContactsBy<FirstName,LastName,phoneNumber,MobileNumber>

    @Test
    public void getContactsByFirstName_returnsMatchingContacts_whenFirstNameIsProvided() throws Exception {
        ContactEntity contactEntity = contactRepository.findAll().get(0);

        mockMvc.perform(get(path + "/getContactByFirstName/" + contactEntity.getFirstName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is(contactEntity.getFirstName())));
    }

    //complete other 3 also

}
