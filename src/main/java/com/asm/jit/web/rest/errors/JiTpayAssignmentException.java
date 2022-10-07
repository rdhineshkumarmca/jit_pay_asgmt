package com.asm.jit.web.rest.errors;

public class JiTpayAssignmentException extends RuntimeException {

    String message;

    public JiTpayAssignmentException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
