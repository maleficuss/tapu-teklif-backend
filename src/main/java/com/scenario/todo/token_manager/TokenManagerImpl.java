package com.scenario.todo.token_manager;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenManagerImpl implements TokenManager{
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.min}")
    private int jwtExpirationMin;


    @Override
    public Key getSigningKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(jwtSecret),
                SignatureAlgorithm.HS256.getJcaName());
    }

    @Override
    public JwtParser getParser() {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build();
    }


    public JwtToken createToken(String username){

        long createdMillis = System.currentTimeMillis();
        Date createdAt = new Date(createdMillis);
        Date expiresAt = new Date(createdMillis + ((long) jwtExpirationMin * 60 * 1000));
        Key secretKey = getSigningKey();

        String token =  Jwts.builder()
                .setSubject(username)
                .signWith(secretKey)
                .setIssuedAt(createdAt)
                .setExpiration(expiresAt)
                .compact();

        return new JwtToken(token);
    }

}
