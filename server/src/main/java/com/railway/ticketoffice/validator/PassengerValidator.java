package com.railway.ticketoffice.validator;

import com.railway.ticketoffice.exception.type.DataNotFoundException;
import com.railway.ticketoffice.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PassengerValidator {

    public static String KEY = "passenger";
    public static String EXIST_MESSAGE_FORMAT = "Passenger#%d doesn't exist";

    @Autowired
    private PassengerRepository passengerRepository;

    public void validateExistence(Long passengerId) throws DataNotFoundException {
        passengerRepository.findById(passengerId).orElseThrow(
                () -> new DataNotFoundException(KEY, String.format(EXIST_MESSAGE_FORMAT, passengerId)));
    }
}
