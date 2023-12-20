package com.hsbc.usermanagement.exceptions;

public class AuthenticationException extends Exception {

    public AuthenticationException(String error) {
        super(error);
    }
}
