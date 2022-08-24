package com.alkemy.ong.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {


    static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour
    protected final String HEADER = "Authorization";
    protected final String PREFIX = "Bearer ";


    protected Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String getJWTToken(String username) {

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SECRET_KEY)
                .compact();

        return token;
    }
}
