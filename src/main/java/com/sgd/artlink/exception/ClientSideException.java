package com.sgd.artlink.exception;

public class ClientSideException extends RuntimeException {

    public ClientSideException() {
        super();
    }

    public ClientSideException(String message) {
        super(message);
    }

    public ClientSideException(String message, Throwable cause) {
        super(message, cause);
    }
}
