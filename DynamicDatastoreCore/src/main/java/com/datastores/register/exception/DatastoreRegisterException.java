package com.datastores.register.exception;

public class DatastoreRegisterException extends Exception {
    public DatastoreRegisterException(String message, Throwable ex) {
        super(message, ex);
    }

    public DatastoreRegisterException(String message) {
        super(message);
    }
}
