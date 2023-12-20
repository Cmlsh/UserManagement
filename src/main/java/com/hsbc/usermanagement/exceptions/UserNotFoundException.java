package com.hsbc.usermanagement.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String error) {
        super(error);
    }
}
