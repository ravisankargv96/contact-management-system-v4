package com.project.contactmanagementsystemv4.contacts;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "contact")
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
