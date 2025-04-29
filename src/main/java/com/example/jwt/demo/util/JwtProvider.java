package com.example.jwt.demo.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
    // private Key key = Keys.hmacShaKeyFor("Queue-key-vert-vert-vert-vert-hard-is-ok".getBytes(StandardCharsets.UTF_8));  

    @Value("${jwt.secret}")
    private String secret;
    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccToken(String email){
        System.out.println("debug >> JwtProvider access Token");
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date()) // 생성시간
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 20)) // 만료시간
                .signWith(getSigningKey())
                .compact();
    }
    public String generateReToken(String email){
        System.out.println("debug >> JwtProvider Refresh Token");
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date()) // 생성시간
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 만료시간
                .signWith(getSigningKey())
                .compact();
    }
}
