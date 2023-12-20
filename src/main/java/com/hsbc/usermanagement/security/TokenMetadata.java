package com.hsbc.usermanagement.security;


import java.time.LocalTime;

public class TokenMetadata {

    public String getAssociatedUser() {
        return associatedUser;
    }

    String associatedUser;
    private Boolean isExpired;
    private Long createdAt;

    private final long duration = 120 * 60 * 1000; // 120 minutes

    public TokenMetadata(String associatedUser, Long createdAt) {
        this.associatedUser = associatedUser;
        this.createdAt = createdAt;
    }

    public Boolean checkIfExpired () {

        long ago = System.currentTimeMillis() - duration;
        if(System.currentTimeMillis() >= this.createdAt + duration) {
            return true;
        }
        return false;
    }

    public void inValidateToken() {
        this.isExpired = true;
    }
}
