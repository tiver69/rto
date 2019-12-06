package com.railway.ticketoffice.validator;

import com.railway.ticketoffice.exception.type.DataNotFoundException;
import com.railway.ticketoffice.exception.type.DataValidationException;
import com.railway.ticketoffice.util.DateTimeUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RequestValidatorTest {

    @Mock
    private StationValidator stationValidator;

    @InjectMocks
    private RequestValidator requestValidator;

    private static String VALID_DATE = "2019-10-27";
    private static Long VALID_DEPARTURE_STATION_ID = 1L;
    private static Long VALID_DESTINATION_STATION_ID = 2L;

    private static String NOT_VALID_DATE = "2019/10/27";
    private static Long NOT_VALID_DEPARTURE_STATION_ID = 12345L;
    private static Long NOT_VALID_DESTINATION_STATION_ID = 12345L;

    @Test
    public void shouldReturnNothingWithValidRequestTrainParam() {
        doNothing().when(stationValidator).validateExistence(VALID_DEPARTURE_STATION_ID);
        doNothing().when(stationValidator).validateExistence(VALID_DESTINATION_STATION_ID);

        requestValidator.validateTrainRequest(VALID_DEPARTURE_STATION_ID,
                VALID_DESTINATION_STATION_ID, VALID_DATE);
        verify(stationValidator).validateExistence(VALID_DEPARTURE_STATION_ID);
        verify(stationValidator).validateExistence(VALID_DESTINATION_STATION_ID);
    }

    @Test(expected = DataValidationException.class)
    public void shouldReturnExceptionWithNotValidRequestData() {
        doThrow(new DataNotFoundException(StationValidator.KEY,
                String.format(StationValidator.EXIST_MESSAGE_FORMAT, NOT_VALID_DESTINATION_STATION_ID)))
                .when(stationValidator).validateExistence(NOT_VALID_DESTINATION_STATION_ID);

        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(RequestValidator.DATE_KEY, DateTimeUtil.VALIDATE_DATE_FORMAT_MESSAGE);
        expectedCauseObject.put(RequestValidator.DEPARTURE_STATION_KEY,
                String.format(StationValidator.EXIST_MESSAGE_FORMAT, NOT_VALID_DEPARTURE_STATION_ID));
        expectedCauseObject.put(RequestValidator.DESTINATION_STATION_KEY,
                String.format(StationValidator.EXIST_MESSAGE_FORMAT, NOT_VALID_DESTINATION_STATION_ID));

        try {
            requestValidator.validateTrainRequest(NOT_VALID_DEPARTURE_STATION_ID,
                    NOT_VALID_DESTINATION_STATION_ID, NOT_VALID_DATE);
        } catch (DataValidationException ex){
            Assert.assertEquals(expectedCauseObject, ex.getCauseObject());
            throw ex;
        }
    }
}
