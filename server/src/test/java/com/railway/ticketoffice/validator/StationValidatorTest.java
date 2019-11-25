package com.railway.ticketoffice.validator;

import com.railway.ticketoffice.exception.type.DataNotFoundException;
import com.railway.ticketoffice.repository.StationRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StationValidatorTest {

    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private StationValidator stationValidator;

    private static Long NOT_EXISTING_ID = 12345L;

    @Test(expected = DataNotFoundException.class)
    public void shouldReturnExceptionWithNotExistingPassengerId() {
        when(stationRepository.findById(NOT_EXISTING_ID)).thenReturn(Optional.empty());

        try {
            stationValidator.validateExistence(NOT_EXISTING_ID);
        } catch (DataNotFoundException ex) {
            String message = ex.getMessage();
            Assert.assertEquals(String.format(StationValidator.EXIST_MESSAGE_FORMAT, NOT_EXISTING_ID),
                    message);
            throw ex;
        }
    }

}