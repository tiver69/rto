package com.railway.ticketoffice.exception.type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataNotFoundException extends RuntimeException {

    private String message;

    public DataNotFoundException(String message) {
        this.message = message;
    }
}
