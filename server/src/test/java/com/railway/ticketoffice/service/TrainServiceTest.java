package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.WeekDay;
import com.railway.ticketoffice.dto.request.train.CoachTypeInfoDto;
import com.railway.ticketoffice.dto.request.train.TrainInfoDto;
import com.railway.ticketoffice.exception.type.DataValidationException;
import com.railway.ticketoffice.repository.StopRepository;
import com.railway.ticketoffice.validator.RequestValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrainServiceTest {

    @Mock
    private RequestValidator requestValidator;

    @Mock
    private StopRepository stopRepository;

    @Mock
    private StopService stopService;

    @Mock
    private CoachService coachService;

    @InjectMocks
    private TrainService trainService;

    private static String VALID_DATE = "2019-10-27";
    private static LocalDate VALID_PARSED_DATE = LocalDate.of(2019, 10, 27);
    private static WeekDay VALID_WEEK_DAY = WeekDay.SUN;
    private static Long VALID_DEPARTURE_STATION_ID = 1L;
    private static Long VALID_DESTINATION_STATION_ID = 2L;

    private static String NOT_VALID_DATE = "2019/10/27";
    private static Long NOT_VALID_DEPARTURE_STATION_ID = 12345L;
    private static Long NOT_VALID_DESTINATION_STATION_ID = 12345L;

    private static Long VALID_TRAIN1_ID = 732L;
    private static Long VALID_TRAIN2_ID = 72L;
    private static LocalTime VALID_DEPARTURE_TIME = LocalTime.of(23, 0);
    private static LocalDate VALID_PARSED_ARRIVAL_DATE = LocalDate.of(2019, 10, 28);
    private static Long VALID_DURATION = 120L;
    private static String VALID_DURATION_STRING = "02:00";

    @Test
    public void shouldReturnTrueWithValidRequestData() {
        TrainInfoDto firstTrain = TrainInfoDto.builder()
                .id(VALID_TRAIN1_ID).departureTime(VALID_DEPARTURE_TIME).build();
        TrainInfoDto secondTrain = TrainInfoDto.builder()
                .id(VALID_TRAIN2_ID).departureTime(VALID_DEPARTURE_TIME).build();
        List<CoachTypeInfoDto> coachTypeForFirstTrain = Collections.singletonList(new CoachTypeInfoDto());
        List<CoachTypeInfoDto> coachTypeForSecondTrain = Collections.singletonList(new CoachTypeInfoDto());
        List<TrainInfoDto> findAllTrainsResult = Arrays.asList(firstTrain, secondTrain);
        TrainInfoDto expectedFirstTrain = TrainInfoDto.builder()
                .id(VALID_TRAIN1_ID)
                .duration(VALID_DURATION_STRING)
                .departureDate(VALID_PARSED_DATE)
                .arrivalDate(VALID_PARSED_ARRIVAL_DATE)
                .departureTime(VALID_DEPARTURE_TIME)
                .coachTypeInfoList(coachTypeForFirstTrain)
                .build();
        TrainInfoDto expectedSecondTrain = TrainInfoDto.builder()
                .id(VALID_TRAIN2_ID)
                .duration(VALID_DURATION_STRING)
                .departureDate(VALID_PARSED_DATE)
                .arrivalDate(VALID_PARSED_ARRIVAL_DATE)
                .departureTime(VALID_DEPARTURE_TIME)
                .coachTypeInfoList(coachTypeForSecondTrain)
                .build();
        List<TrainInfoDto> expected = Arrays.asList(expectedFirstTrain, expectedSecondTrain);
        doNothing().when(requestValidator)
                .validateTrainRequest(VALID_DEPARTURE_STATION_ID, VALID_DESTINATION_STATION_ID, VALID_DATE);
        when(stopRepository.findAllTrainsByDirectionAndWeekDay(VALID_DEPARTURE_STATION_ID,
                VALID_DESTINATION_STATION_ID, VALID_WEEK_DAY))
                .thenReturn(findAllTrainsResult);
        when(stopService.countTrainDirectionDurationInMinutes(
                VALID_TRAIN1_ID, VALID_DEPARTURE_STATION_ID, VALID_DESTINATION_STATION_ID)).thenReturn(VALID_DURATION);
        when(stopService.countTrainDirectionDurationInMinutes(
                VALID_TRAIN2_ID, VALID_DEPARTURE_STATION_ID, VALID_DESTINATION_STATION_ID)).thenReturn(VALID_DURATION);
        when(coachService.findAllCoachTypesInfoByTrainIdAndDepartureDate(VALID_TRAIN1_ID, VALID_PARSED_DATE))
                .thenReturn(coachTypeForFirstTrain);
        when(coachService.findAllCoachTypesInfoByTrainIdAndDepartureDate(VALID_TRAIN2_ID, VALID_PARSED_DATE))
                .thenReturn(coachTypeForSecondTrain);

        List<TrainInfoDto> result = trainService.findAllTrainsInDirectionAtDate(VALID_DEPARTURE_STATION_ID,
                VALID_DESTINATION_STATION_ID, VALID_DATE);
        Assert.assertEquals(expected, result);
    }

    @Test(expected = DataValidationException.class)
    public void shouldReturnExceptionWithNotValidRequestData() {
        String expectedMessage = "Message";
        doThrow(new DataValidationException(expectedMessage, new HashMap<>())).when(requestValidator)
                .validateTrainRequest(NOT_VALID_DEPARTURE_STATION_ID,
                        NOT_VALID_DESTINATION_STATION_ID, NOT_VALID_DATE);

        try {
            trainService.findAllTrainsInDirectionAtDate(NOT_VALID_DEPARTURE_STATION_ID,
                    NOT_VALID_DESTINATION_STATION_ID, NOT_VALID_DATE);
        } catch (DataValidationException ex) {
            String resultMessage = ex.getMessage();
            Assert.assertEquals(expectedMessage, resultMessage);
            verify(requestValidator)
                    .validateTrainRequest(NOT_VALID_DEPARTURE_STATION_ID,
                            NOT_VALID_DESTINATION_STATION_ID, NOT_VALID_DATE);
            verify(stopRepository, never()).findAllTrainsByDirectionAndWeekDay(NOT_VALID_DEPARTURE_STATION_ID,
                    NOT_VALID_DESTINATION_STATION_ID, VALID_WEEK_DAY);
            throw ex;
        }
    }
}
