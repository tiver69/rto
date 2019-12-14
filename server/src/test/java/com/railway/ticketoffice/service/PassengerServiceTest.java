package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.dto.PageableDto;
import com.railway.ticketoffice.dto.PassengerDto;
import com.railway.ticketoffice.exception.type.DataNotFoundException;
import com.railway.ticketoffice.exception.type.DataValidationException;
import com.railway.ticketoffice.repository.PassengerRepository;
import com.railway.ticketoffice.util.PageUtil;
import com.railway.ticketoffice.validator.PassengerValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.railway.ticketoffice.util.PageUtil.ITEMS_PER_PAGE;
import static com.railway.ticketoffice.validator.PassengerValidator.EXIST_MESSAGE_FORMAT_LOGIN;
import static com.railway.ticketoffice.validator.PassengerValidator.KEY;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PassengerServiceTest {

    @InjectMocks
    PassengerService passengerService;

    @Mock
    PassengerValidator passengerValidator;

    @Mock
    PassengerRepository passengerRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private static int VALID_PAGE = 1;
    private static int NOT_VALID_PAGE = 4;
    private static int TOTAL_PAGES = 3;
    private static Long NOT_EXISTING_ID = 12345L;
    private static Long EXISTING_ID = 1L;
    private static String EXISTING_LOGIN = "Login";
    private static String NOT_EXISTING_LOGIN = "Not_login";
    private static final Passenger PASSENGER = Passenger.builder()
            .id(1L)
            .firstName("Firstname")
            .lastName("Secondname")
            .login("login123")
            .build();

    @Test
    public void shouldReturnCorrectPageWithPassengerDtoWithValidPageRequestData() {
        List<PassengerDto> content = Collections.emptyList();
        Pageable pageable = PageRequest.of(VALID_PAGE, ITEMS_PER_PAGE);
        PageImpl page = new PageImpl<>(content, pageable, PageUtil.ITEMS_PER_PAGE * TOTAL_PAGES);
        when(passengerRepository.findPagePassengersInfo(pageable)).thenReturn(page);

        PageableDto<PassengerDto> result = passengerService.findPageForManaging(VALID_PAGE);
        Assert.assertEquals(result.getCurrentPage(), content);
        Assert.assertEquals(result.getTotalPages(), TOTAL_PAGES);
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldReturnPageWithPassengerDtoWithValidPageRequestData() {
        List<PassengerDto> content = Collections.emptyList();
        Pageable pageable = PageRequest.of(NOT_VALID_PAGE, ITEMS_PER_PAGE);
        PageImpl page = new PageImpl<>(content, pageable, PageUtil.ITEMS_PER_PAGE * TOTAL_PAGES);
        when(passengerRepository.findPagePassengersInfo(pageable)).thenReturn(page);

        try {
            PageableDto<PassengerDto> result = passengerService.findPageForManaging(NOT_VALID_PAGE);
        } catch (DataNotFoundException ex) {
            String result = ex.getMessage();
            Assert.assertEquals(String.format(PageUtil.EXIST_MESSAGE_FORMAT, NOT_VALID_PAGE), result);
            throw ex;
        }
    }

    @Test
    public void shouldReturnNotEmptyPassengerWithValidPassengerCreateData() {
        String password = "password";
        String encodedPassword = "passwordpassword";
        Passenger createdPassenger = Passenger.builder()
                .id(EXISTING_ID + 1)
                .firstName(PASSENGER.getFirstName())
                .lastName(PASSENGER.getLastName())
                .login(PASSENGER.getLogin())
                .password(encodedPassword)
                .build();
        PASSENGER.setId(null);
        PASSENGER.setPassword(password);
        PASSENGER.setConfirmPassword(password);
        when(passengerRepository.save(PASSENGER)).thenReturn(createdPassenger);
        when(bCryptPasswordEncoder.encode(PASSENGER.getPassword())).thenReturn(encodedPassword);
        doNothing().when(passengerValidator).validateCreate(PASSENGER);

        Passenger result = passengerService.save(PASSENGER);
        verify(passengerValidator).validateCreate(PASSENGER);
        verify(passengerRepository).save(PASSENGER);
        Assert.assertEquals(createdPassenger, result);
    }

    @Test(expected = DataValidationException.class)
    public void shouldReturnExceptionWithNotValidCreatePassengerData() {
        String expectedMessage = "Message";
        doThrow(new DataValidationException(expectedMessage, new HashMap<>()))
                .when(passengerValidator).validateCreate(PASSENGER);

        try {
            passengerService.save(PASSENGER);
        } catch (DataValidationException ex) {
            String resultMessage = ex.getMessage();
            Assert.assertEquals(expectedMessage, resultMessage);
            verify(passengerValidator).validateCreate(PASSENGER);
            verify(passengerRepository, never()).save(PASSENGER);
            throw ex;
        }
    }

    @Test
    public void shouldReturnTrueWithValidPassengerUpdateData() {
        when(passengerRepository.update(PASSENGER.getId(), PASSENGER.getFirstName(),
                PASSENGER.getLastName(), PASSENGER.getLogin()))
                .thenReturn(1);
        doNothing().when(passengerValidator).validateUpdate(PASSENGER);

        Boolean result = passengerService.updatePassenger(PASSENGER);
        verify(passengerValidator).validateUpdate(PASSENGER);
        verify(passengerRepository).update(PASSENGER.getId(), PASSENGER.getFirstName(),
                PASSENGER.getLastName(), PASSENGER.getLogin());
        Assert.assertTrue(result);
    }

    @Test(expected = DataValidationException.class)
    public void shouldReturnExceptionWithNotValidUpdatePassenger() {
        String expectedMessage = "Message";
        doThrow(new DataValidationException(expectedMessage, new HashMap<>()))
                .when(passengerValidator).validateUpdate(PASSENGER);

        try {
            passengerService.updatePassenger(PASSENGER);
        } catch (DataValidationException ex) {
            String resultMessage = ex.getMessage();
            Assert.assertEquals(expectedMessage, resultMessage);
            verify(passengerValidator).validateUpdate(PASSENGER);
            verify(passengerRepository, never()).update(PASSENGER.getId(), PASSENGER.getFirstName(),
                    PASSENGER.getLastName(), PASSENGER.getLogin());
            throw ex;
        }
    }

    @Test
    public void shouldReturnTrueWithValidPassengerRemoveId() {
        when(passengerValidator.validateExistenceAndReturn(EXISTING_ID)).thenReturn(PASSENGER);
        when(passengerRepository.removeById(EXISTING_ID)).thenReturn(1);

        Boolean result = passengerService.removePassenger(EXISTING_ID);
        verify(passengerValidator).validateExistenceAndReturn(EXISTING_ID);
        verify(passengerRepository).removeById(EXISTING_ID);
        Assert.assertTrue(result);
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldReturnExceptionWithNotExistingPassengerRemoveId() {
        doThrow(new DataNotFoundException(KEY,
                String.format(PassengerValidator.EXIST_MESSAGE_FORMAT_ID, NOT_EXISTING_ID)))
                .when(passengerValidator).validateExistenceAndReturn(NOT_EXISTING_ID);

        try {
            passengerService.removePassenger(NOT_EXISTING_ID);
        } catch (DataNotFoundException ex) {
            String message = ex.getMessage();
            Assert.assertEquals(String.format(PassengerValidator.EXIST_MESSAGE_FORMAT_ID, NOT_EXISTING_ID),
                    message);
            verify(passengerValidator).validateExistenceAndReturn(NOT_EXISTING_ID);
            verify(passengerRepository, never()).removeById(NOT_EXISTING_ID);
            throw ex;
        }
    }

    @Test
    public void shouldReturnPassengerWithExistingLoginRequest() {
        Passenger existingPassenger = new Passenger();
        when(passengerValidator.validateExistenceAndReturn(EXISTING_LOGIN)).thenReturn(existingPassenger);

        Passenger result = (Passenger) passengerService.loadUserByUsername(EXISTING_LOGIN);
        Assert.assertEquals(existingPassenger, result);
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldThrowExceptionWithNotExistingLoginRequest() {
        String expectedMessage = String.format(EXIST_MESSAGE_FORMAT_LOGIN, NOT_EXISTING_LOGIN);
        when(passengerValidator.validateExistenceAndReturn(NOT_EXISTING_LOGIN)).thenThrow(
                new DataNotFoundException(KEY, expectedMessage));

        try {
            Passenger result = (Passenger) passengerService.loadUserByUsername(NOT_EXISTING_LOGIN);
        }catch (DataNotFoundException ex){
            Assert.assertEquals(expectedMessage, ex.getMessage());
            throw ex;
        }
    }
}
