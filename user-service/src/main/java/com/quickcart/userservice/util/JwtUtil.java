package com.quickcart.userservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey key;
    private final long jwtExpirationMs;

    public JwtUtil(@Value("${app.jwt.secret}") String Secrete, @Value("${app.jwt.expiration-ms}") long jwtExpirationMs) {
        this.key= Keys.hmacShaKeyFor(Secrete.getBytes());
        this.jwtExpirationMs=jwtExpirationMs;
    }

    public String generateToken(String userId, String email, String role){
        long now=System.currentTimeMillis();

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("email",email)
                .claim("role",role)
                .issuedAt(new Date(now))
                .expiration(new Date(now+ jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    public Jws<Claims> validateToken(String token){
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
    }

    public String getUserIdFromToken(String token){
        Claims claims= validateToken(token).getPayload();
        return claims.getSubject();
    }
}
