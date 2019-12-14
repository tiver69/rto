package com.railway.ticketoffice.validator;

import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.exception.type.DataNotFoundException;
import com.railway.ticketoffice.exception.type.DataValidationException;
import com.railway.ticketoffice.repository.PassengerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Transactional
public class PassengerValidatorTest {

    @Mock
    PassengerRepository passengerRepository;

    @InjectMocks
    PassengerValidator passengerValidator;

    private static Long NOT_EXISTING_ID = 12345L;
    private static String VALID_FIRST_NAME = "Firstname";
    private static String NOT_VALID_FIRST_NAME = "Firstname1";
    private static String VALID_LAST_NAME = "Secondname";
    private static String NOT_VALID_LAST_NAME = "Secondname1";
    private static String VALID_LOGIN = "login123";
    private static String NOT_VALID_LOGIN = "notvalidlogin!";
    private static String EXISTING_LOGIN = "test11";
    private static String PASSWORD = "password";

    private Passenger newPassenger;
    private static final Passenger EXISTING_PASSENGER = Passenger.builder()
            .id(2L)
            .firstName(VALID_FIRST_NAME)
            .lastName(VALID_LAST_NAME)
            .login(EXISTING_LOGIN)
            .password(PASSWORD)
            .build();

    @Before
    public void beforeEach() {
        newPassenger = Passenger.builder()
                .id(1L)
                .firstName(VALID_FIRST_NAME)
                .lastName(VALID_LAST_NAME)
                .login(VALID_LOGIN)
                .password(PASSWORD)
                .build();

        when(passengerRepository.findById(newPassenger.getId())).thenReturn(Optional.of(newPassenger));
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldReturnExceptionWithNotExistingPassengerId() {
        when(passengerRepository.findByLogin(NOT_VALID_LOGIN)).thenReturn(Optional.empty());

        try {
            passengerValidator.validateExistenceAndReturn(NOT_VALID_LOGIN);
        } catch (DataNotFoundException ex) {
            String message = ex.getMessage();
            Assert.assertEquals(String.format(PassengerValidator.EXIST_MESSAGE_FORMAT_LOGIN, NOT_VALID_LOGIN),
                    message);
            throw ex;
        }
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldReturnExceptionWithNotExistingPassengerLogin() {
        when(passengerRepository.findById(NOT_EXISTING_ID)).thenReturn(Optional.empty());

        try {
            passengerValidator.validateExistenceAndReturn(NOT_EXISTING_ID);
        } catch (DataNotFoundException ex) {
            String message = ex.getMessage();
            Assert.assertEquals(String.format(PassengerValidator.EXIST_MESSAGE_FORMAT_ID, NOT_EXISTING_ID),
                    message);
            throw ex;
        }
    }

    @Test(expected = DataValidationException.class)
    public void shouldReturnExceptionWithNotValidBasicPassengerUpdateData() {
        newPassenger.setFirstName(NOT_VALID_FIRST_NAME);
        newPassenger.setLastName(NOT_VALID_LAST_NAME);
        newPassenger.setLogin(NOT_VALID_LOGIN);

        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(PassengerValidator.KEY_LOGIN, PassengerValidator.VALIDATE_LOGIN_MESSAGE);
        expectedCauseObject.put(PassengerValidator.KEY_FIRST_NAME, PassengerValidator.VALIDATE_FIRST_NAME_MESSAGE);
        expectedCauseObject.put(PassengerValidator.KEY_LAST_NAME, PassengerValidator.VALIDATE_LAST_NAME_MESSAGE);

        try {
            passengerValidator.validateUpdate(newPassenger);
        } catch (DataValidationException ex) {
            HashMap<String, String> causeObject = ex.getCauseObject();
            Assert.assertEquals(expectedCauseObject, causeObject);
            throw ex;
        }
    }

    @Test(expected = DataValidationException.class)
    public void shouldReturnExceptionWithNotValidPassengerCreateData() {
        newPassenger.setId(null);
        newPassenger.setFirstName(NOT_VALID_FIRST_NAME);
        newPassenger.setLastName(NOT_VALID_LAST_NAME);
        newPassenger.setLogin(NOT_VALID_LOGIN);
        newPassenger.setConfirmPassword(PASSWORD + "NOT_THE_SAME");

        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(PassengerValidator.KEY_LOGIN, PassengerValidator.VALIDATE_LOGIN_MESSAGE);
        expectedCauseObject.put(PassengerValidator.KEY_FIRST_NAME, PassengerValidator.VALIDATE_FIRST_NAME_MESSAGE);
        expectedCauseObject.put(PassengerValidator.KEY_LAST_NAME, PassengerValidator.VALIDATE_LAST_NAME_MESSAGE);
        expectedCauseObject.put(PassengerValidator.KEY_CONFIRM_PASSWORD, PassengerValidator.VALIDATE_CONFIRM_PASSWORD_MESSAGE);

        try {
            passengerValidator.validateCreate(newPassenger);
        } catch (DataValidationException ex) {
            HashMap<String, String> causeObject = ex.getCauseObject();
            Assert.assertEquals(expectedCauseObject, causeObject);
            throw ex;
        }
    }

    @Test(expected = DataValidationException.class)
    public void shouldReturnExceptionWithExistingPassengerUpdateLogin() {
        newPassenger.setLogin(EXISTING_LOGIN);
        when(passengerRepository.findByLogin(EXISTING_LOGIN)).thenReturn(Optional.of(EXISTING_PASSENGER));

        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(PassengerValidator.KEY_LOGIN, PassengerValidator.VALIDATE_EXISTENCE_LOGIN_MESSAGE);

        try {
            passengerValidator.validateUpdate(newPassenger);
        } catch (DataValidationException ex) {
            HashMap<String, String> causeObject = ex.getCauseObject();
            Assert.assertEquals(expectedCauseObject, causeObject);
            throw ex;
        }
    }

    @Test(expected = DataValidationException.class)
    public void shouldReturnExceptionWithExistingPassengerCreateLogin() {
        newPassenger.setId(null);
        newPassenger.setLogin(EXISTING_LOGIN);
        newPassenger.setConfirmPassword(PASSWORD);
        when(passengerRepository.findByLogin(EXISTING_LOGIN)).thenReturn(Optional.of(EXISTING_PASSENGER));

        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(PassengerValidator.KEY_LOGIN, PassengerValidator.VALIDATE_EXISTENCE_LOGIN_MESSAGE);

        try {
            passengerValidator.validateCreate(newPassenger);
        } catch (DataValidationException ex) {
            HashMap<String, String> causeObject = ex.getCauseObject();
            Assert.assertEquals(expectedCauseObject, causeObject);
            throw ex;
        }
    }

    @Test(expected = DataValidationException.class)
    @Rollback
    public void shouldReturnExceptionWithNotExistingPassengerUpdateId() {
        newPassenger.setId(NOT_EXISTING_ID);

        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(PassengerValidator.KEY, String.format(PassengerValidator.EXIST_MESSAGE_FORMAT_ID, NOT_EXISTING_ID));

        try {
            passengerValidator.validateUpdate(newPassenger);
        } catch (DataValidationException ex) {
            Assert.assertEquals(expectedCauseObject, ex.getCauseObject());
            throw ex;
        }
    }
}