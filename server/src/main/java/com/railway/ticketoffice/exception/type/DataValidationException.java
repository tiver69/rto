package com.railway.ticketoffice.exception.type;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class DataValidationException extends RuntimeException {

    private String message;
    private HashMap<String, String> causeObject;

    public DataValidationException(String message) {
        this.message = message;
    }
}
