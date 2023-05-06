package com.project.contactmanagementsystemv4.security.authtokens;

import com.project.contactmanagementsystemv4.users.UserEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthTokenService {

    private final AuthTokenRepository authTokenRepository;

    public AuthTokenService(AuthTokenRepository authTokenRepository) {
        this.authTokenRepository = authTokenRepository;
    }

    public UUID createAuthToken(UserEntity userEntity) {
        AuthTokenEntity authTokenEntity = new AuthTokenEntity();
        authTokenEntity.setUser(userEntity);
        AuthTokenEntity savedAuthToken = authTokenRepository.save(authTokenEntity);
        return savedAuthToken.getId();
    }

    public Long getUserIdFromAuthToken(UUID authToken) {
        AuthTokenEntity savedAuthToken = authTokenRepository.findById(authToken)
                .orElseThrow(() -> new BadCredentialsException("Invalid Auth token"));
        return savedAuthToken.getUser().getId();
    }
}
