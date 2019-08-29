package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.Stop;
import com.railway.ticketoffice.domain.Ticket;
import com.railway.ticketoffice.dto.TicketDto;
import com.railway.ticketoffice.repository.StopRepository;
import com.railway.ticketoffice.repository.TicketRepository;
import com.railway.ticketoffice.util.DateTimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.railway.ticketoffice.util.DateTimeUtil.DATE_TIME_FORMATTER;

@Service
public class TicketService {

    private static Logger LOG = Logger.getLogger(TicketService.class);
    private static Integer ITEMS_PER_PAGE = 5;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private StopRepository stopRepository;

    public List<TicketDto> findAllByPassenger(Long id) throws IllegalArgumentException {

        if (!passengerService.checkIfExistById(id)) throw new IllegalArgumentException();
        List<Ticket> tickets = ticketRepository.findAllByPassengerId(id);
        List<TicketDto> ticketsDto = new ArrayList<>();

        tickets.forEach(ticket -> {
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
                                    .atTime(departureTime).format(DATE_TIME_FORMATTER))
                            .arrivalDateTime(arrivalDateTime.format(DATE_TIME_FORMATTER))
                            .place(ticket.getPlace())
                            .price(ticket.getPrice())
                            .isActive(DateTimeUtil.isFuture(arrivalDateTime))
                            .build());
        });

        LOG.info(String.format("Tickets request for passenger#%d - found %d", id, ticketsDto.size()));
        return ticketsDto;
    }

    public List<TicketDto> findPageByPassenger(Long id, Integer page, Boolean isActive) throws IllegalArgumentException {
        if (!passengerService.checkIfExistById(id)) throw new IllegalArgumentException();
        Pageable pageable = PageRequest.of(page, ITEMS_PER_PAGE);
        List<TicketDto> ticketsDto;

        if (isActive) ticketsDto = ticketRepository.findActivePageByPassengerId(id, LocalDate.now(), pageable).getContent();
        else ticketsDto = ticketRepository.findHistoryPageByPassengerId(id, LocalDate.now(), pageable).getContent();

        return ticketsDto;
    }

    public Ticket save(Ticket ticket) {
//        TO_DO: proper validation
        return ticketRepository.save(ticket);
    }

    public Integer countTicketPrice(Long trainId, Long trainCoachId,
                                    Long departureStationId, Long destinationStationId) {
        return stopRepository.countPriceByDirectionAndTrainCoachId(trainId, trainCoachId,
                departureStationId, destinationStationId).orElseThrow(IllegalArgumentException::new);
    }
}
