package com.revature.service;


import com.revature.model.User;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.Key;


public class JWTService {
    Logger logger = LoggerFactory.getLogger(JWTService.class);

    private Key key;
    public JWTService() {
        byte[] secret = System.getenv("secret").getBytes(StandardCharsets.UTF_8);
        key = Keys.hmacShaKeyFor(secret);
    }

    public String createJWT(User user) {

        String jwt = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("user_id", user.getId())
                .claim("user_role_id", user.getUserRole().getId())
                .signWith(key)
                .compact();
        return jwt;
    }

    public Jws<Claims> parseJwt(String jwt) {
        try {
            Jws<Claims> token = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);

            return token;
        } catch(SignatureException e) {
            e.getMessage();
            throw new UnauthorizedResponse("Invalid JWT\n" + e.getMessage());
        } catch(JwtException e) {
            e.printStackTrace();
            throw new UnauthorizedResponse("Invalid JWT\n" + e.getMessage());
        }
    }


}
