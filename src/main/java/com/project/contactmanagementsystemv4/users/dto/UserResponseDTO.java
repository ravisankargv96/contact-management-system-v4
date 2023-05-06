package com.project.contactmanagementsystemv4.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {
    Long id;
    String email;
    String username;
    String token;
}
