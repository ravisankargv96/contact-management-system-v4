package com.project.contactmanagementsystemv4.users;

import com.project.contactmanagementsystemv4.security.authtokens.AuthTokenService;
import com.project.contactmanagementsystemv4.users.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.project.contactmanagementsystemv4.security.jwt.JWTService;

@Service
public class UserService {
    static enum AuthType {
        JWT,
        AUTH_TOKEN
    }
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;
    private final AuthTokenService authTokenService;

    //later add jwt & authTokenservice
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JWTService jwtService, AuthTokenService authTokenService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authTokenService = authTokenService;
    }

    public UserResponseDTO createUser(CreateUserDTO createUserDTO) {
        var newUserEntity = modelMapper.map(createUserDTO, UserEntity.class);
        newUserEntity.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        UserEntity savedUser = userRepository.save(newUserEntity);
        UserResponseDTO userResponseDTO = modelMapper.map(savedUser, UserResponseDTO.class);

        userResponseDTO.setToken(jwtService.createJWT(savedUser.getId()));
        return userResponseDTO;
    }

    public UserResponseDTO loginUser(LoginUserDTO loginUserDTO, AuthType authType) {
        UserEntity userEntity = userRepository.findByUsername(loginUserDTO.getUsername());
        if(userEntity == null) {
            throw new UserNotFoundException(loginUserDTO.getUsername());
        }
        boolean passwordMatches = passwordEncoder.matches(loginUserDTO.getPassword(), userEntity.getPassword());
        if(!passwordMatches) {
            throw new IllegalArgumentException("Incorrect password");
        }

        UserResponseDTO userResponseDTO = modelMapper.map(userEntity, UserResponseDTO.class);

        switch(authType) {
            case JWT:
                //userResponseDTO.setToken(jwtServices.generateToken(userEntity.getId()));
                break;
            case AUTH_TOKEN:
                //userResponseDTO.setToken(authTokenService.generateToken(userEntity.getId()));
                break;
            default:
                throw new IllegalArgumentException("Invalid auth type");
        }

        return userResponseDTO;
    }

    // Exceptions defined in this class -> refactor it into separate classes
    public static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(Integer id) {
            super("User with id: " + id + " not found");
        }

        public UserNotFoundException(String username) {
            super("User with username: " + username + " not found");
        }
    }

    public static class IncorrectPasswordException extends IllegalArgumentException {
        public IncorrectPasswordException() {
            super("Incorrect password");
        }
    }

}
