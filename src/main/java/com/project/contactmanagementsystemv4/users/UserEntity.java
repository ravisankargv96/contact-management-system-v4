package com.project.contactmanagementsystemv4.users;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;


@Entity(name = "users")
@Setter
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @CreatedDate()
    @Column(name = "created_at", updatable = false)
    Date createdAt;

    @Column(unique = true, nullable = false, length = 50)
    String username;
    String password;
    String email;

}
