package com.railway.ticketoffice.exception.type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataNotFoundException extends RuntimeException {

    private String key;
    private String message;

    public DataNotFoundException(String message) {
        this.message = message;
    }
    public DataNotFoundException(String key, String message) {
        this.key = key;
        this.message = message;
    }
}
