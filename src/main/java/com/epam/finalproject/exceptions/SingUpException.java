package com.epam.finalproject.exceptions;

public class SingUpException extends RuntimeException {
    private static final long serialVersionUID = 5861310537366287163L;

    public SingUpException() {
        super();
    }

    public SingUpException(String message) {
        super(message);
    }

    public SingUpException(String message, Throwable cause) {
        super(message, cause);
    }

    public SingUpException(Throwable cause) {
        super(cause);
    }
}
