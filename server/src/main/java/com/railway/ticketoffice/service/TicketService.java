package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.Ticket;
import com.railway.ticketoffice.dto.TicketDto;
import com.railway.ticketoffice.repository.StopRepository;
import com.railway.ticketoffice.repository.TicketRepository;
import com.railway.ticketoffice.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private StopService stopService;

    @Autowired
    private StopRepository stopRepository;


    public List<TicketDto> findPageByPassenger(Long passengerId, Integer page, Boolean isActive) {
        passengerService.checkIfExistById(passengerId);

        Page<TicketDto> ticketsDtoPage = ticketRepository.findPageByPassengerIdAndActiveStatus(
                passengerId, LocalDate.now(), isActive, PageUtil.getPageableFromPageNumber(page));
        PageUtil.checkPageBounds(page, ticketsDtoPage);

        MDC.put("passengerId", passengerId.toString());
        LOGGER.info("Tickets active({}) page#{} request - found {}", isActive, page, ticketsDtoPage.getContent().size());
        return ticketsDtoPage.getContent();
    }

    public Integer countPageByPassenger(Long passengerId, Boolean isActive) {
        passengerService.checkIfExistById(passengerId);
        return ticketRepository.findPageByPassengerIdAndActiveStatus(
                passengerId, LocalDate.now(), isActive, PageUtil.getPageableFromPageNumber(0))
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
                                    Long departureStationId, Long destinationStationId) throws IllegalArgumentException {
        Integer result = stopRepository.countPriceByDirectionAndTrainCoachId(trainId, trainCoachId,
                departureStationId, destinationStationId)
                .orElseThrow(() -> new IllegalArgumentException("Request for ticket price counting went wrong"));
        LOGGER.info("Request for ticket price for train#{} in coach#{} from station#{} - to station#{} - price is {}",
                trainId, trainCoachId, departureStationId, destinationStationId, result);
        return result;
    }
}
