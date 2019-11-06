package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.exception.type.DataNotFoundException;
import com.railway.ticketoffice.exception.type.DataValidationException;
import com.railway.ticketoffice.validator.PassengerValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class PassengerServiceTest {

    @Autowired
    PassengerService passengerService;

    private Passenger passenger;

    private static Long NOT_EXISTING_ID = 12345L;
    private static String NOT_VALID_FIRST_NAME = "Aleksandra1";
    private static String NOT_VALID_LAST_NAME = "Aleksandra1";
    private static String NOT_VALID_LOGIN = "notvalidlogin!";
    private static String EXISTING_LOGIN = "test11";


    @Before
    public void beforeEach() {
        passenger = Passenger.builder()
                .id(1L)
                .firstName("Firstname")
                .lastName("Secondname")
                .login("login123")
                .build();
    }

    @Test
    @Rollback
    public void shouldReturnTrueWithValidPassengerUpdateData() {
        Boolean result = passengerService.updatePassenger(passenger);
        Assert.assertTrue(result);
    }

    @Test(expected = DataValidationException.class)
    @Rollback
    public void shouldReturnExceptionWithNotValidPassengerUpdateData() {
        passenger.setFirstName(NOT_VALID_FIRST_NAME);
        passenger.setLastName(NOT_VALID_LAST_NAME);
        passenger.setLogin(NOT_VALID_LOGIN);

        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(PassengerValidator.KEY_LOGIN, PassengerValidator.VALIDATE_LOGIN_MESSAGE);
        expectedCauseObject.put(PassengerValidator.KEY_FIRST_NAME, PassengerValidator.VALIDATE_FIRST_NAME_MESSAGE);
        expectedCauseObject.put(PassengerValidator.KEY_LAST_NAME, PassengerValidator.VALIDATE_LAST_NAME_MESSAGE);

        try {
            passengerService.updatePassenger(passenger);
        } catch (DataValidationException ex) {
            HashMap<String, String> causeObject = ex.getCauseObject();
            Assert.assertEquals(expectedCauseObject, causeObject);
            throw ex;
        }
    }

    @Test(expected = DataValidationException.class)
    @Rollback
    public void shouldReturnExceptionWithExistingPassengerUpdateLogin() {
        passenger.setLogin(EXISTING_LOGIN);

        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(PassengerValidator.KEY_LOGIN, PassengerValidator.VALIDATE_EXISTENCE_LOGIN_MESSAGE);

        try {
            passengerService.updatePassenger(passenger);
        } catch (DataValidationException ex) {
            HashMap<String, String> causeObject = ex.getCauseObject();
            Assert.assertEquals(expectedCauseObject, causeObject);
            throw ex;
        }
    }

    @Test(expected = DataValidationException.class)
    @Rollback
    public void shouldReturnExceptionWithNotExistingPassengerUpdateId() {
        passenger.setId(NOT_EXISTING_ID);

        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(PassengerValidator.KEY, String.format(PassengerValidator.EXIST_MESSAGE_FORMAT, NOT_EXISTING_ID));

        try {
            passengerService.updatePassenger(passenger);
        } catch (DataValidationException ex) {
            Assert.assertEquals(expectedCauseObject, ex.getCauseObject());
            throw ex;
        }
    }

    @Test(expected = DataNotFoundException.class)
    @Rollback
    public void shouldReturnExceptionWithNotExistingPassengerRemoveId() {
        try {
            passengerService.removePassenger(NOT_EXISTING_ID);
        } catch (DataNotFoundException ex) {
            String message = ex.getMessage();
            Assert.assertEquals(String.format(PassengerValidator.EXIST_MESSAGE_FORMAT, NOT_EXISTING_ID),
                    message);
            throw ex;
        }
    }

}