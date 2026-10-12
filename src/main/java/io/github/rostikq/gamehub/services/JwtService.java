package io.github.rostikq.gamehub.services;

import io.github.rostikq.gamehub.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private final String secret = "test-key-mazaka-hello-secret-key-i-love-dgdgdgdg";

    public String generateToken(User user){
        return Jwts.builder().subject(user.getUsername())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 10800000))
                .signWith(getKey())
                .compact();
    }

    public String extractName(String token){
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public String extractRole(String token){
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
