package com.revature.service;


import com.revature.model.User;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;


public class JWTService {
    Logger logger = LoggerFactory.getLogger(JWTService.class);

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String createJWT(User user) {
        String jwt = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("user_id", user.getId())
                .claim("user_role", user.getUserRole())
                .signWith(key)
                .compact();
        return jwt;
    }

    public Jws<Claims> parseJwt(String jwt) {
        try {
            Jws<Claims> token = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);

            return token;
        } catch(JwtException e) {
            e.printStackTrace();
            logger.warn("Invalid JWT");
            throw new UnauthorizedResponse("Invalid JWT");
        }
    }


}
