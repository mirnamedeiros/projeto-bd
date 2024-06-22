package com.imd.comasy.exceptions;

public class AuthenticationInvalidException extends RuntimeException {

    public AuthenticationInvalidException() {
        super("Authentication failed due to invalid credentials. Please check your username and password and try again.");
    }
}
