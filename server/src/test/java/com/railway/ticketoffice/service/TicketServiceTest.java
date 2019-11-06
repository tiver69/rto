package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.*;
import com.railway.ticketoffice.exception.type.DataValidationException;
import com.railway.ticketoffice.validator.PassengerValidator;
import com.railway.ticketoffice.validator.StationValidator;
import com.railway.ticketoffice.validator.TrainCoachValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    private Ticket ticket;

    private static long NOT_EXISTING_COACH_ID = 31L;
    private static long NOT_EXISTING_PASSENGER_ID = 12345L;
    private static long NOT_EXISTING_STATION_ID = 0L;

    private static int EXPECTED_PRICE = 472;

    @Before
    public void beforeEach() {
        ticket = Ticket.builder()
                .passenger(Passenger.builder().id(1L).build())
                .departureStation(Station.builder().id(2L).build())
                .destinationStation(Station.builder().id(1L).build())
                .departureDate(LocalDate.now().plusDays(1))
                .arrivalDate(LocalDate.now().plusDays(2))
                .trainCoach(TrainCoach.builder().id(20L).train(Train.builder().id(732L).build()).build())
                .place(11)
                .build();
    }

    @Test
    @Ignore //TO_DO: unignore with test db
    public void shouldCountCorrectPrice() {
        int actualPrice = ticketService.countTicketPrice(ticket.getTrainCoach().getTrain().getId(), ticket.getTrainCoach().getId(),
                ticket.getDepartureStation().getId(), ticket.getDestinationStation().getId());
        Assert.assertEquals(actualPrice, EXPECTED_PRICE);
    }

    @Test
    @Rollback
    public void shouldReturnNotEmptyTicketWithValidParam() {
        Ticket resultTicket = ticketService.save(ticket);
        Assert.assertTrue(resultTicket.getId() != 0);
    }

    @Test(expected = DataValidationException.class)
    @Rollback
    public void shouldReturnExceptionWithNotValidParam() {
        ticket.setTrainCoach(TrainCoach.builder().id(NOT_EXISTING_COACH_ID).train(Train.builder().id(732L).build()).build());
        ticket.setPassenger(Passenger.builder().id(NOT_EXISTING_PASSENGER_ID).build());
        ticket.setDepartureStation(Station.builder().id(NOT_EXISTING_STATION_ID).build());

        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(TrainCoachValidator.KEY,
                String.format(TrainCoachValidator.EXIST_COACH_IN_TRAIN_MESSAGE_FORMAT, NOT_EXISTING_COACH_ID, 732L));
        expectedCauseObject.put(PassengerValidator.KEY,
                String.format(PassengerValidator.EXIST_MESSAGE_FORMAT, NOT_EXISTING_PASSENGER_ID));
        expectedCauseObject.put(StationValidator.KEY,
                String.format(StationValidator.EXIST_MESSAGE_FORMAT, NOT_EXISTING_STATION_ID));

        try {
            ticketService.save(ticket);
        } catch (DataValidationException ex) {
            HashMap<String, String> causeObject = ex.getCauseObject();
            Assert.assertEquals(expectedCauseObject, causeObject);
            throw ex;
        }
    }
}