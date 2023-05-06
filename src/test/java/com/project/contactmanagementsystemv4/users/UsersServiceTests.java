package com.project.contactmanagementsystemv4.users;

import com.project.contactmanagementsystemv4.security.authtokens.AuthTokenService;
import com.project.contactmanagementsystemv4.security.jwt.JWTService;
import com.project.contactmanagementsystemv4.users.dto.CreateUserDTO;
import com.project.contactmanagementsystemv4.users.dto.UserResponseDTO;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UsersServiceTests {

    @Autowired
    private UserRepository userRepository;
    private UserService userService;

    private UserService getUserService() {
        if (userService == null) {
            ModelMapper modelMapper = new ModelMapper();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            JWTService jwtService = new JWTService();
            AuthTokenService authTokenService = null;
            userService = new UserService(
                    userRepository,
                    modelMapper,
                    passwordEncoder,
                    jwtService,
                    authTokenService
            );
        }
        return userService;
    }

    @Test
    public void testCreateUser() {
        CreateUserDTO newUser = new CreateUserDTO();
        newUser.setEmail("test@email.com");
        newUser.setUsername("testuser");
        newUser.setPassword("testpassword");
        UserResponseDTO savedUser = getUserService().createUser(newUser);
        assertNotNull(savedUser);
    }

}
