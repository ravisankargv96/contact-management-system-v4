package com.project.contactmanagementsystemv4.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    private Algorithm algorithm = Algorithm.HMAC256("SECRET SIGNING KEY (should be in env or config)");

    public String createJWT (Long userId) {
        return createJWT(
                userId,
                new Date(),
                new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7) //7 days
        );
    }

    protected String createJWT (Long userId, Date issuedAt, Date expiresAt) {
        String token =  JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(algorithm);

        return token;
    }

    public Long getUserIdFromJWT (String jwt) {
        DecodedJWT decodedJWT = JWT.decode(jwt);
        String subject = decodedJWT.getSubject();
        return Long.parseLong(subject);
    }
}
