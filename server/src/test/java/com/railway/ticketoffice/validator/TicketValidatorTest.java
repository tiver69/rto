package com.railway.ticketoffice.validator;

import com.railway.ticketoffice.domain.*;
import com.railway.ticketoffice.exception.type.DataNotFoundException;
import com.railway.ticketoffice.exception.type.DataValidationException;
import com.railway.ticketoffice.repository.TrainCoachRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TicketValidatorTest {

    @Mock
    private TrainCoachRepository trainCoachRepository;

    @Mock
    private TrainCoachValidator trainCoachValidator;

    @Mock
    private PassengerValidator passengerValidator;

    @Mock
    private StationValidator stationValidator;

    @InjectMocks
    TicketValidator ticketValidator;

    private static long EXISTING_TRAIN_ID = 732L;
    private static int NUMBER_OF_PLACES_IN_COACH_TYPE = 15;
    private static long NOT_EXISTING_COACH_ID = 0L;
    private static long NOT_EXISTING_STATION_ID = 0L;

    private static final TrainCoach TRAIN_COACH = TrainCoach.builder()
            .id(1L)
            .train(Train.builder().id(EXISTING_TRAIN_ID).build())
            .coachType(CoachType.builder().id(1L).places(NUMBER_OF_PLACES_IN_COACH_TYPE).build())
            .number(1)
            .build();

    private Ticket ticket;

    @Before
    public void beforeEach() {
        ticket = Ticket.builder()
                .passenger(Passenger.builder().id(1L).build())
                .departureStation(Station.builder().id(2L).build())
                .destinationStation(Station.builder().id(1L).build())
                .departureDate(LocalDate.now().plusDays(1))
                .arrivalDate(LocalDate.now().plusDays(2))
                .trainCoach(TRAIN_COACH)
                .place(NUMBER_OF_PLACES_IN_COACH_TYPE - 1)
                .build();
    }

    @Test
    public void shoudReturnOkWithValidParam() {
        doNothing().when(trainCoachValidator).validateExistenceCoachInTrain(ticket.getTrainCoach().getId(),
                ticket.getTrainCoach().getTrain().getId());
        doNothing().when(stationValidator).validateExistence(ticket.getDestinationStation().getId());
        doNothing().when(stationValidator).validateExistence(ticket.getDepartureStation().getId());
        when(trainCoachRepository.findById(ticket.getTrainCoach().getId())).thenReturn(Optional.of(TRAIN_COACH));

        ticketValidator.validate(ticket);
        verify(trainCoachValidator).validateExistenceCoachInTrain(ticket.getTrainCoach().getId(),
                ticket.getTrainCoach().getTrain().getId());
        verify(stationValidator).validateExistence(ticket.getDepartureStation().getId());
        verify(stationValidator).validateExistence(ticket.getDestinationStation().getId());
    }

    @Test(expected = DataValidationException.class)
    public void shouldReturnExceptionWithNotExistingTicketParam() {
        doThrow(new DataNotFoundException(TrainCoachValidator.KEY,
                String.format(TrainCoachValidator.EXIST_COACH_IN_TRAIN_MESSAGE_FORMAT,
                        NOT_EXISTING_COACH_ID, EXISTING_TRAIN_ID)))
                .when(trainCoachValidator).validateExistenceCoachInTrain(NOT_EXISTING_COACH_ID, EXISTING_TRAIN_ID);
        doThrow(new DataNotFoundException(StationValidator.KEY,
                String.format(StationValidator.EXIST_MESSAGE_FORMAT, NOT_EXISTING_STATION_ID)))
                .when(stationValidator).validateExistence(NOT_EXISTING_STATION_ID);

        ticket.setTrainCoach(TrainCoach.builder().id(NOT_EXISTING_COACH_ID)
                .train(Train.builder().id(EXISTING_TRAIN_ID).build()).build());
        ticket.setDepartureStation(Station.builder().id(NOT_EXISTING_STATION_ID).build());

        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(TrainCoachValidator.KEY,
                String.format(TrainCoachValidator.EXIST_COACH_IN_TRAIN_MESSAGE_FORMAT,
                        NOT_EXISTING_COACH_ID, EXISTING_TRAIN_ID));
        expectedCauseObject.put(StationValidator.KEY,
                String.format(StationValidator.EXIST_MESSAGE_FORMAT, NOT_EXISTING_STATION_ID));

        try {
            ticketValidator.validate(ticket);
        } catch (DataValidationException ex) {
            HashMap<String, String> causeObject = ex.getCauseObject();
            Assert.assertEquals(expectedCauseObject, causeObject);
            throw ex;
        }
    }

    @Test(expected = DataValidationException.class)
    public void shouldReturnExceptionWithNotExistingPlaceParam() {
        when(trainCoachRepository.findById(ticket.getTrainCoach().getId())).thenReturn(Optional.of(TRAIN_COACH));

        ticket.setPlace(NUMBER_OF_PLACES_IN_COACH_TYPE + 10);
        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(TicketValidator.PLACE_KEY,
                String.format(TicketValidator.PLACE_MESSAGE, ticket.getPlace(), ticket.getTrainCoach().getId()));

        try {
            ticketValidator.validate(ticket);
        } catch (DataValidationException ex) {
            HashMap<String, String> causeObject = ex.getCauseObject();
            Assert.assertEquals(expectedCauseObject, causeObject);
            verify(trainCoachRepository).findById(ticket.getTrainCoach().getId());
            throw ex;
        }
    }
}
