package com.railway.ticketoffice.validator;

import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.exception.type.DataNotFoundException;
import com.railway.ticketoffice.exception.type.DataValidationException;
import com.railway.ticketoffice.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class PassengerValidator {

    public static String KEY = "passenger";
    public static String EXIST_MESSAGE_FORMAT = "Passenger#%d doesn't exist";

    public static String KEY_LOGIN = "login";
    public static String KEY_FIRST_NAME = "firstName";
    public static String KEY_LAST_NAME = "lastName";
    public static String VALIDATE_LOGIN_MESSAGE = "Login must have literals, numeric as well as - or _ symbols and be from 3 to 16 symbols long.";
    public static String VALIDATE_FIRST_NAME_MESSAGE = "First name must contain only symbols and start from capital";
    public static String VALIDATE_LAST_NAME_MESSAGE = "Last name must contain only symbols and start from capital";
    public static String VALIDATE_EXISTENCE_LOGIN_MESSAGE = "Passenger with such login already exists!";


    @Autowired
    private PassengerRepository passengerRepository;

    public void validateExistence(Long passengerId) throws DataNotFoundException {
        passengerRepository.findById(passengerId).orElseThrow(
                () -> new DataNotFoundException(KEY, String.format(EXIST_MESSAGE_FORMAT, passengerId)));
    }

    public void validate(Passenger passenger) {
        HashMap<String, String> causeObject = new HashMap<>();
        String loginPattern = "[A-Za-z0-9_-]{3,16}";
        String namePattern = "[A-ZА-Я][A-Za-zА-Яа-я-]*";

        if (!passenger.getLogin().matches(loginPattern))
            causeObject.put(KEY_LOGIN, VALIDATE_LOGIN_MESSAGE);
        if (!passenger.getFirstName().matches(namePattern))
            causeObject.put(KEY_FIRST_NAME, VALIDATE_FIRST_NAME_MESSAGE);
        if (!passenger.getLastName().matches(namePattern))
            causeObject.put(KEY_LAST_NAME, VALIDATE_LAST_NAME_MESSAGE);

        try { validateExistence(passenger.getId()); }
        catch (DataNotFoundException ex){
            causeObject.put(ex.getKey(), ex.getMessage());
            throw new DataValidationException("Passengers data is not valid!", causeObject);
        }
        passengerRepository.findByLogin(passenger.getLogin()).ifPresent(
                existingPassenger -> {
                    if (!existingPassenger.getId().equals(passenger.getId()))
                        causeObject.put(KEY_LOGIN, VALIDATE_EXISTENCE_LOGIN_MESSAGE);
                });
        if (!causeObject.isEmpty()) throw new DataValidationException("Passengers data is not valid!", causeObject);
    }
}
