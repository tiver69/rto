package com.railway.ticketoffice.service;

import com.railway.ticketoffice.dto.request.coach.CoachInfoDto;
import com.railway.ticketoffice.dto.request.train.CoachTypeInfoDto;
import com.railway.ticketoffice.exception.type.DataNotFoundException;
import com.railway.ticketoffice.exception.type.DataValidationException;
import com.railway.ticketoffice.repository.TicketRepository;
import com.railway.ticketoffice.repository.TrainCoachRepository;
import com.railway.ticketoffice.util.DateTimeUtil;
import com.railway.ticketoffice.validator.TrainCoachValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CoachServiceTest {

    private static Long VALID_TRAIN_ID = 72L;
    private static LocalDate VALID_DEPARTURE_DATE = LocalDate.of(2019, 8, 29);
    private static String NOT_VALID_DEPARTURE_DATE = "2019/08/29";
    private static Integer VALID_COACH_NUMBER = 1;
    private static Integer NOT_VALID_COACH_NUMBER = 0;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TrainCoachRepository trainCoachRepository;

    @InjectMocks
    private CoachService coachService;

    @Test
    public void shouldReturnCorrectCoachTypeListWithCountAvailablePlaces() {
        long totalPlaces0 = 180L;
        long totalPlaces1 = 162L;
        long availablePlaces0 = 80L;
        long availablePlaces1 = 62L;
        List<CoachTypeInfoDto> coachTypesByTrainIdList = Arrays.asList(
                new CoachTypeInfoDto("Compartment", 5L, totalPlaces0),
                new CoachTypeInfoDto("Berth", 3L, totalPlaces1));
        List<CoachTypeInfoDto> bookedPlacesInCoachTypeList = Arrays.asList(
                new CoachTypeInfoDto("Compartment", availablePlaces0),
                new CoachTypeInfoDto("Berth", availablePlaces1));
        List<CoachTypeInfoDto> expected = Arrays.asList(
                new CoachTypeInfoDto("Compartment", 5L, totalPlaces0),
                new CoachTypeInfoDto("Berth", 3L, totalPlaces1));
        expected.get(0).setAvailablePlaces(totalPlaces0 - availablePlaces0);
        expected.get(1).setAvailablePlaces(totalPlaces1 - availablePlaces1);
        when(trainCoachRepository.countAllCoachTypesByTrainId(VALID_TRAIN_ID)).thenReturn(coachTypesByTrainIdList);
        when(ticketRepository.countBookedPlacesInCoachTypeByTrainIdAndDepartureDate(VALID_TRAIN_ID,
                VALID_DEPARTURE_DATE)).thenReturn(bookedPlacesInCoachTypeList);

        List<CoachTypeInfoDto> result = coachService.findAllCoachTypesInfoByTrainIdAndDepartureDate(
                VALID_TRAIN_ID, VALID_DEPARTURE_DATE);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void shouldReturnCorrectCoachInfoWithBookedAndAvailablePlaces() {
        int totalPlaces = 36;
        int bookedPlacesSize = 4;
        List<Integer> bookedPlaces = Arrays.asList(17, 18, 19, 20);
        CoachInfoDto coachByNumber = new CoachInfoDto(22L, 3L, "Compartment", 1, totalPlaces);
        CoachInfoDto expected = new CoachInfoDto(22L, 3L, "Compartment", 1, totalPlaces);
        expected.setBookedPlaces(bookedPlaces);
        expected.setAvailablePlaces(totalPlaces - bookedPlacesSize);
        when(trainCoachRepository.findCoachByNumberAndTrainId(VALID_TRAIN_ID, VALID_COACH_NUMBER)).thenReturn(
                Optional.of(coachByNumber));
        when(ticketRepository.findAllBookedPlacesByCoachNumberAndTrainIdAndDepartureDate(VALID_TRAIN_ID,
                VALID_DEPARTURE_DATE, VALID_COACH_NUMBER)).thenReturn(bookedPlaces);

        CoachInfoDto result = coachService.findCoachInfoByTrainIdDepartureDateAndCoachNumber(VALID_TRAIN_ID,
                VALID_DEPARTURE_DATE.toString(), VALID_COACH_NUMBER);
        Assert.assertEquals(expected, result);
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldReturnExceptionWithNotExistingCoachNumber() {
        try {
            coachService.findCoachInfoByTrainIdDepartureDateAndCoachNumber(VALID_TRAIN_ID,
                    VALID_DEPARTURE_DATE.toString(), NOT_VALID_COACH_NUMBER);
            verify(coachService, never()).findCoachInfoByTrainIdDepartureDateAndCoachNumber(VALID_TRAIN_ID,
                    VALID_DEPARTURE_DATE.toString(), NOT_VALID_COACH_NUMBER);
        } catch (DataNotFoundException ex) {
            Assert.assertEquals(ex.getMessage(), String.format(TrainCoachValidator.EXIST_COACH_IN_TRAIN_MESSAGE_FORMAT,
                    NOT_VALID_COACH_NUMBER, VALID_TRAIN_ID));
            throw ex;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldReturnExceptionWithNotValidDate() {
        try {
            coachService.findCoachInfoByTrainIdDepartureDateAndCoachNumber(VALID_TRAIN_ID, NOT_VALID_DEPARTURE_DATE,
                    VALID_COACH_NUMBER);
            verify(coachService, never()).findCoachInfoByTrainIdDepartureDateAndCoachNumber(VALID_TRAIN_ID,
                    NOT_VALID_DEPARTURE_DATE, VALID_COACH_NUMBER);
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals(DateTimeUtil.VALIDATE_DATE_FORMAT_MESSAGE, ex.getMessage());
            throw ex;
        }
    }
}
