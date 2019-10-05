package com.railway.ticketoffice.exception.type;

public class UnexpectedException extends RuntimeException {

    private String message;

    public UnexpectedException(String message) {
        this.message = message;
    }
}
