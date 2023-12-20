package com.hsbc.usermanagement.exceptions;

public class InvalidAuthTokenException extends Exception {

    public InvalidAuthTokenException(String error) {
        super(error);
    }
}
