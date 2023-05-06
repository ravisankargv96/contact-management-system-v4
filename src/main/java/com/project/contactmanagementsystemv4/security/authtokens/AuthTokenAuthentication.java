package com.project.contactmanagementsystemv4.security.authtokens;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthTokenAuthentication implements Authentication {
    private String token;

    private Long userId;


    public AuthTokenAuthentication(String token) {
        this.token = token;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // implementing overridden methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getCredentials() {
        return null;
    }

    @Override
    public String getDetails() {
        return null;
    }

    @Override
    public Integer getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return userId != null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
