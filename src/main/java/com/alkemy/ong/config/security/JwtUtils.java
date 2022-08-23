package com.alkemy.ong.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {


    static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour
    protected final String HEADER = "Authorization";
    protected final String PREFIX = "Bearer ";
//    private final String SECRET = "mySecretKey";

    protected Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String getJWTToken(String username) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts.builder()
                .setSubject("Alkemy")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SECRET_KEY)
                .compact();

        return token;
    }
}
