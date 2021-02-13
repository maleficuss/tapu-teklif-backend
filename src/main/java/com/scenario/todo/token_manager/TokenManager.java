package com.scenario.todo.token_manager;

import io.jsonwebtoken.JwtParser;

import java.security.Key;

public interface TokenManager {
    JwtToken createToken(String username);
    Key getSigningKey();
    JwtParser getParser();
}
