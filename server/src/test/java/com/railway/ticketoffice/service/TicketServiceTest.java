package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.*;
import com.railway.ticketoffice.exception.type.DataValidationException;
import com.railway.ticketoffice.repository.StopRepository;
import com.railway.ticketoffice.repository.TicketRepository;
import com.railway.ticketoffice.validator.PassengerValidator;
import com.railway.ticketoffice.validator.TicketValidator;
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
public class TicketServiceTest {

    @Mock
    private TicketValidator ticketValidator;

    @Mock
    private PassengerValidator passengerValidator;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private StopRepository stopRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;

    @Before
    public void beforeEach() {
        ticket = Ticket.builder()
                .id(1L)
                .passenger(Passenger.builder().id(1L).build())
                .departureStation(Station.builder().id(2L).build())
                .destinationStation(Station.builder().id(1L).build())
                .departureDate(LocalDate.now().plusDays(1))
                .arrivalDate(LocalDate.now().plusDays(2))
                .trainCoach(TrainCoach.builder().id(2L).train(Train.builder().id(732L).build()).build())
                .place(11)
                .price(100)
                .build();
    }

    @Test
    public void shouldCountCorrectPrice() {
        int expected = 1000;
        when(stopRepository.countPriceByDirectionAndTrainCoachId(
                ticket.getTrainCoach().getTrain().getId(), ticket.getTrainCoach().getId(),
                ticket.getDepartureStation().getId(), ticket.getDestinationStation().getId()))
                .thenReturn(Optional.of(expected));

        int result = ticketService.countTicketPrice(ticket.getTrainCoach().getTrain().getId(),
                ticket.getTrainCoach().getId(), ticket.getDepartureStation().getId(),
                ticket.getDestinationStation().getId());
        Assert.assertEquals(expected, result);
    }

    @Test
    public void shouldReturnNotEmptyTicketWithValidParam() {
        int returnPrice = 1000;
        ticket.setPrice(returnPrice);
        doNothing().when(ticketValidator).validate(ticket);
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        when(stopRepository.countPriceByDirectionAndTrainCoachId(
                ticket.getTrainCoach().getTrain().getId(), ticket.getTrainCoach().getId(),
                ticket.getDepartureStation().getId(), ticket.getDestinationStation().getId()))
                .thenReturn(Optional.of(returnPrice));

        Ticket resultTicket = ticketService.save(ticket);
        verify(ticketRepository).save(ticket);
        Assert.assertEquals(resultTicket, ticket);
    }

    @Test(expected = DataValidationException.class)
    public void shouldReturnExceptionWithNotValidParam() {
        String expectedMessage = "Message";
        doThrow(new DataValidationException(expectedMessage, new HashMap<>()))
                .when(ticketValidator).validate(ticket);

        try {
            ticketService.save(ticket);
        } catch (DataValidationException ex) {
            String resultMessage = ex.getMessage();
            Assert.assertEquals(expectedMessage, resultMessage);
            verify(ticketValidator).validate(ticket);
            verify(ticketRepository, never()).save(ticket);
            throw ex;
        }
    }
}
