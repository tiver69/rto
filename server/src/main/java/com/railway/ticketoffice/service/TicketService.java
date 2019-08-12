package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.Stop;
import com.railway.ticketoffice.domain.Ticket;
import com.railway.ticketoffice.dto.TicketDto;
import com.railway.ticketoffice.repository.StopRepository;
import com.railway.ticketoffice.repository.TicketRepository;
import com.railway.ticketoffice.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private StopRepository stopRepository;

    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public List<TicketDto> findAllByPassenger(Long id) {

        List<Ticket> tickets = ticketRepository.findAllByPassengerId(id);
        List<TicketDto> ticketsDto = new ArrayList<>();

        tickets.stream().forEach(ticket -> {
            Long trainId = ticket.getTrainCoach().getTrain().getId();
            Long departureStationId = ticket.getDepartureStation().getId();
            Long destinationStationId = ticket.getDestinationStation().getId();
            LocalDate departureDate = ticket.getDepartureDate();
            LocalTime departureTime = stopRepository.
                    findByTrainIdAndStationId(trainId, departureStationId).map(Stop::getDeparture)
                    .orElseThrow(IllegalArgumentException::new);
            LocalTime arrivalTime = stopRepository.
                    findByTrainIdAndStationId(trainId, destinationStationId).map(Stop::getArrival)
                    .orElseThrow(IllegalArgumentException::new);

            LocalDateTime arrivalDateTime = DateTimeUtil.getArrivalDate(departureDate, departureTime, arrivalTime)
                    .atTime(arrivalTime);

            ticketsDto.add(
                    TicketDto.builder()
                            .id(ticket.getId())
                            .departureStation(ticket.getDepartureStation().getName())
                            .destinationStation(ticket.getDestinationStation().getName())
                            .trainId(trainId)
                            .coachNumber(ticket.getTrainCoach().getNumber())
                            .departureDateTime(departureDate
                                    .atTime(departureTime).format(FORMATTER))
                            .arrivalDateTime(arrivalDateTime.format(FORMATTER))
                            .place(ticket.getPlace())
                            .price(ticket.getPrice())
                            .isActive(DateTimeUtil.isFuture(arrivalDateTime))
                            .build());
        });
        return ticketsDto;
    }


}
