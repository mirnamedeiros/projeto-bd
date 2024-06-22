package com.imd.comasy.exceptions;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException() {
        super("User or password already exists.");
    }
}
