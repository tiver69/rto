package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.Ticket;
import com.railway.ticketoffice.dto.TicketDto;
import com.railway.ticketoffice.repository.StopRepository;
import com.railway.ticketoffice.repository.TicketRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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



    public List<TicketDto> findPageByPassenger(Long id, Integer page, Boolean isActive) throws IllegalArgumentException {
        if (!passengerService.checkIfExistById(id)) throw new IllegalArgumentException();
        Pageable pageable = PageRequest.of(page, ITEMS_PER_PAGE);
        List<TicketDto> ticketsDto;

        if (isActive)
            ticketsDto = ticketRepository.findActivePageByPassengerId(id, LocalDate.now(), pageable).getContent();
        else ticketsDto = ticketRepository.findHistoryPageByPassengerId(id, LocalDate.now(), pageable).getContent();

        LOG.info(String.format("Tickets page#%d request for passenger#%d - found %d", page, id, ticketsDto.size()));
        return ticketsDto;
    }

    public Integer countPageByPassenger(Long id, Boolean isActive) throws IllegalArgumentException {
        if (!passengerService.checkIfExistById(id)) throw new IllegalArgumentException();
        Pageable pageable = PageRequest.of(0, ITEMS_PER_PAGE);

        if (isActive)
            return ticketRepository.findActivePageByPassengerId(id, LocalDate.now(), pageable)
                    .getTotalPages();

        return ticketRepository.findHistoryPageByPassengerId(id, LocalDate.now(), pageable).getTotalPages();
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
