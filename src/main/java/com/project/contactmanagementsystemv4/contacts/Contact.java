package com.project.contactmanagementsystemv4.contacts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contact {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
