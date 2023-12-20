package com.hsbc.usermanagement.security;

import com.hsbc.usermanagement.exceptions.InvalidAuthTokenException;
import com.hsbc.usermanagement.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class TokenManager {

    private static volatile  TokenManager tokenManager;
    private static Object lock = new Object();

    private static Map<String, TokenMetadata> tokens = new HashMap<String, TokenMetadata>();

    public static TokenManager getInstance() {

        TokenManager manager = tokenManager;
        if(manager == null) {
            synchronized (lock) {
                manager = tokenManager;
                if(manager == null) {
                    manager = tokenManager = new TokenManager();
                }
            }
        }
        return manager;
    }

    public String createToekn(String username) {

        String randomString = StringUtils.generateRandomString();
        TokenMetadata tokenMetadata = new TokenMetadata(username, System.currentTimeMillis());
        tokens.put(randomString, tokenMetadata);

        return randomString;
    }

    public boolean isTokenValid(String token) {

        if(!tokens.containsKey(token)) {
            return false;
        } else {

            TokenMetadata tokenMetadata = tokens.get(token);
            Boolean isExpired = tokenMetadata.checkIfExpired();

            if(isExpired) {
                return false;
            } else {
                return true;
            }
        }
    }

    public TokenMetadata getTokenUser(String token) throws InvalidAuthTokenException {

        if(isTokenValid(token)) {
            return tokens.get(token);
        } else {
            throw new InvalidAuthTokenException("Invalid access token.");
        }

    }

    public void invalidateToken(String token) throws InvalidAuthTokenException {
        if(!tokens.containsKey(token)) {
            throw new InvalidAuthTokenException("Invalid access token.");
        } else {

            TokenMetadata tokenMetadata = tokens.get(token);
            tokenMetadata.inValidateToken();
            tokens.remove(token);
        }
    }
}
