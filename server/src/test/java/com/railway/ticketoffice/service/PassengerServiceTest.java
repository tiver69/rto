package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.exception.type.DataNotFoundException;
import com.railway.ticketoffice.exception.type.DataValidationException;
import com.railway.ticketoffice.repository.PassengerRepository;
import com.railway.ticketoffice.validator.PassengerValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PassengerServiceTest {

    @InjectMocks
    PassengerService passengerService;

    @Mock
    PassengerValidator passengerValidator;

    @Mock
    PassengerRepository passengerRepository;

    private static Long NOT_EXISTING_ID = 12345L;
    private static Long EXISTING_ID = 1L;
    private static final Passenger PASSENGER = Passenger.builder()
            .id(1L)
            .firstName("Firstname")
            .lastName("Secondname")
            .login("login123")
            .build();

    @Test
    public void shouldReturnTrueWithValidPassengerUpdateData() {
        when(passengerRepository.update(PASSENGER.getId(), PASSENGER.getFirstName(),
                PASSENGER.getLastName(), PASSENGER.getLogin()))
                .thenReturn(1);
        doNothing().when(passengerValidator).validate(PASSENGER);

        Boolean result = passengerService.updatePassenger(PASSENGER);
        verify(passengerValidator).validate(PASSENGER);
        verify(passengerRepository).update(PASSENGER.getId(), PASSENGER.getFirstName(),
                PASSENGER.getLastName(), PASSENGER.getLogin());
        Assert.assertTrue(result);
    }

    @Test(expected = DataValidationException.class)
    public void shouldReturnExceptionWithNotValidUpdatePassenger() {
        String expectedMessage = "Message";
        doThrow(new DataValidationException(expectedMessage, new HashMap<>()))
                .when(passengerValidator).validate(PASSENGER);

        try {
            passengerService.updatePassenger(PASSENGER);
        } catch (DataValidationException ex) {
            String resultMessage = ex.getMessage();
            Assert.assertEquals(expectedMessage, resultMessage);
            verify(passengerValidator).validate(PASSENGER);
            verify(passengerRepository, never()).update(PASSENGER.getId(), PASSENGER.getFirstName(),
                    PASSENGER.getLastName(), PASSENGER.getLogin());
            throw ex;
        }
    }

    @Test
    public void shouldReturnTrueWithValidPassengerRemoveId() {
        doNothing().when(passengerValidator).validateExistence(EXISTING_ID);
        when(passengerRepository.removeById(EXISTING_ID)).thenReturn(1);

        Boolean result = passengerService.removePassenger(EXISTING_ID);
        verify(passengerValidator).validateExistence(EXISTING_ID);
        verify(passengerRepository).removeById(EXISTING_ID);
        Assert.assertTrue(result);
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldReturnExceptionWithNotExistingPassengerRemoveId() {
        doThrow(new DataNotFoundException(PassengerValidator.KEY,
                String.format(PassengerValidator.EXIST_MESSAGE_FORMAT, NOT_EXISTING_ID)))
                .when(passengerValidator).validateExistence(NOT_EXISTING_ID);

        try {
            passengerService.removePassenger(NOT_EXISTING_ID);
        } catch (DataNotFoundException ex) {
            String message = ex.getMessage();
            Assert.assertEquals(String.format(PassengerValidator.EXIST_MESSAGE_FORMAT, NOT_EXISTING_ID),
                    message);
            verify(passengerValidator).validateExistence(NOT_EXISTING_ID);
            verify(passengerRepository, never()).removeById(NOT_EXISTING_ID);
            throw ex;
        }
    }
}
