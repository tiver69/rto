package com.railway.ticketoffice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.railway.ticketoffice.exception.type.UnexpectedException;

import java.io.IOException;

public class CloneUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static <T> T makeClone(T object) {
        try {
            String serializedObject = objectMapper.writeValueAsString(object);
            return (T) objectMapper.readValue(serializedObject, object.getClass());
        } catch (IOException ex) {
            throw new UnexpectedException("Unexpected error while cloning object");
        }
    }
}
