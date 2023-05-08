package com.project.contactmanagementsystemv4.users;

import com.project.contactmanagementsystemv4.users.dto.CreateUserDTO;
import com.project.contactmanagementsystemv4.users.dto.LoginUserDTO;
import com.project.contactmanagementsystemv4.users.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/cms/api/v1/users") //"cms/api/v1/contacts"
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register") //change registerUser instead of createUser
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserDTO createUserDTO) {
        UserResponseDTO savedUser = userService.createUser(createUserDTO);
        return ResponseEntity
                .created(URI.create("/users/" + savedUser.getId()))
                .body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(
            @RequestBody LoginUserDTO loginUserDTO,
            @RequestParam(name = "token", defaultValue = "jwt") String token) {

        UserService.AuthType authType = UserService.AuthType.JWT;
        if(token.equals("auth_token")) {
            authType = UserService.AuthType.AUTH_TOKEN;
        }
        UserResponseDTO savedUser = userService.loginUser(loginUserDTO, authType);
        return ResponseEntity.ok(savedUser);
    }

    @ExceptionHandler(UserService.UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserService.UserNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
