package com.asm.jit.web.rest.errors;

public class BadRequestAlertException extends RuntimeException {

    String message;

    public BadRequestAlertException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
