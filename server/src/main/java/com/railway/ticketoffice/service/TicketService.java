package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.Ticket;
import com.railway.ticketoffice.dto.TicketDto;
import com.railway.ticketoffice.repository.StopRepository;
import com.railway.ticketoffice.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketService.class);
    private static final Integer ITEMS_PER_PAGE = 5;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private StopService stopService;

    @Autowired
    private StopRepository stopRepository;


    public List<TicketDto> findPageByPassenger(Long passengerId, Integer page, Boolean isActive) throws IllegalArgumentException {
        if (!passengerService.checkIfExistById(passengerId)) throw new IllegalArgumentException();
        Pageable pageable = PageRequest.of(page, ITEMS_PER_PAGE);
        List<TicketDto> ticketsDto;

        if (isActive)
            ticketsDto = ticketRepository.findActivePageByPassengerId(passengerId, LocalDate.now(), pageable).getContent();
        else
            ticketsDto = ticketRepository.findHistoryPageByPassengerId(passengerId, LocalDate.now(), pageable).getContent();

        MDC.put("passengerId", passengerId.toString());
        LOGGER.info("Tickets active({}) page#{} request - found {}",isActive, page, ticketsDto.size());
        return ticketsDto;
    }

    public Integer countPageByPassenger(Long passengerId, Boolean isActive) throws IllegalArgumentException {
        if (!passengerService.checkIfExistById(passengerId)) throw new IllegalArgumentException();
        Pageable pageable = PageRequest.of(0, ITEMS_PER_PAGE);

        if (isActive)
            return ticketRepository.findActivePageByPassengerId(passengerId, LocalDate.now(), pageable)
                    .getTotalPages();

        return ticketRepository.findHistoryPageByPassengerId(passengerId, LocalDate.now(), pageable)
                .getTotalPages();
    }

    public Ticket save(Ticket ticket) {
//        TO_DO: proper validation
        LocalDate arrivalDate = stopService.countArrivalDateFromRootDuration(ticket.getDepartureDate(),
                ticket.getTrainCoach().getTrain().getId(),
                ticket.getDepartureStation().getId(),
                ticket.getDestinationStation().getId());

        ticket.setArrivalDate(arrivalDate);
        return ticketRepository.save(ticket);
    }

    public Integer countTicketPrice(Long trainId, Long trainCoachId,
                                    Long departureStationId, Long destinationStationId) {
        Integer result = stopRepository.countPriceByDirectionAndTrainCoachId(trainId, trainCoachId,
                departureStationId, destinationStationId).orElseThrow(IllegalArgumentException::new);
        LOGGER.info("Request for ticket price for train#{} in coach#{} from station#{} - to station#{} - price is {}",
                trainId, trainCoachId, departureStationId, destinationStationId, result);
        return result;
    }
}
