package com.railway.ticketoffice.validator;

import com.railway.ticketoffice.exception.type.DataNotFoundException;
import com.railway.ticketoffice.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StationValidator {

    public static String KEY = "station";
    public static String EXIST_MESSAGE_FORMAT = "Station#%d doesn't exist";

    @Autowired
    private StationRepository stationRepository;

    public void validateExistence(Long stationId) throws DataNotFoundException {
        stationRepository.findById(stationId).orElseThrow(
                () -> new DataNotFoundException(KEY, String.format(EXIST_MESSAGE_FORMAT, stationId)));
    }
}
