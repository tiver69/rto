package com.railway.ticketoffice.validator;

import com.railway.ticketoffice.exception.type.DataNotFoundException;
import com.railway.ticketoffice.exception.type.DataValidationException;
import com.railway.ticketoffice.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class RequestValidator {

    @Autowired
    private StationValidator stationValidator;

    public static String DATE_KEY = "departureDate";
    public static String DEPARTURE_STATION_KEY = "departureStation";
    public static String DESTINATION_STATION_KEY = "destinationStation";

    public void validateTrainRequest(Long departureStationId, Long destinationStationId, String departureDate) {
        HashMap<String, String> causeObject = new HashMap<>();

        try {
            stationValidator.validateExistence(departureStationId);
        } catch (DataNotFoundException ex) {
            causeObject.put(DEPARTURE_STATION_KEY, ex.getMessage());
        }

        try {
            stationValidator.validateExistence(destinationStationId);
        } catch (DataNotFoundException ex) {
            causeObject.put(DESTINATION_STATION_KEY, ex.getMessage());
        }

        try {
            DateTimeUtil.parseString(departureDate);
        } catch (IllegalArgumentException ex) {
            causeObject.put(DATE_KEY, ex.getMessage());
        }

        if (!causeObject.isEmpty()) throw new DataValidationException("Ticket is not valid!", causeObject);
    }
}
