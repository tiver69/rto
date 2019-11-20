package com.railway.ticketoffice.validator;

import com.railway.ticketoffice.exception.type.DataValidationException;
import com.railway.ticketoffice.service.TrainService;
import com.railway.ticketoffice.util.DateTimeUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RequestValidatorTest {

    @Autowired
    private TrainService trainService;

    private static String VALID_DATE = "2019-10-27";
    private static Long VALID_DEPARTURE_STATION_ID = 1L;
    private static Long VALID_DESTINATION_STATION_ID = 2L;

    private static String NOT_VALID_DATE = "2019/10/27";
    private static Long NOT_VALID_DEPARTURE_STATION_ID = 12345L;
    private static Long NOT_VALID_DESTINATION_STATION_ID = 12345L;


    @Test
    @Rollback
    public void shouldReturnTrueWithValidPassengerUpdateData() {
        int resultSize = trainService.findAllTrainsInDirectionAtDate(VALID_DEPARTURE_STATION_ID,
                VALID_DESTINATION_STATION_ID, VALID_DATE).size();
        int expectedResultSize = 1;

        Assert.assertEquals(resultSize, expectedResultSize);
    }

    @Test(expected = DataValidationException.class)
    @Rollback
    public void shouldReturnExceptionWithNotValidRequestData() {

        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(RequestValidator.DATE_KEY, DateTimeUtil.VALIDATE_DATE_FORMAT_MESSAGE);
        expectedCauseObject.put(RequestValidator.DEPARTURE_STATION_KEY,
                String.format(StationValidator.EXIST_MESSAGE_FORMAT, NOT_VALID_DEPARTURE_STATION_ID));
        expectedCauseObject.put(RequestValidator.DESTINATION_STATION_KEY,
                String.format(StationValidator.EXIST_MESSAGE_FORMAT, NOT_VALID_DESTINATION_STATION_ID));

        try {
            trainService.findAllTrainsInDirectionAtDate(NOT_VALID_DEPARTURE_STATION_ID,
                    NOT_VALID_DESTINATION_STATION_ID, NOT_VALID_DATE);
        } catch (DataValidationException ex){
            Assert.assertEquals(expectedCauseObject, ex.getCauseObject());
            throw ex;
        }
    }
}