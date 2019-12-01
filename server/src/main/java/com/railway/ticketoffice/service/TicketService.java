package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.Ticket;
import com.railway.ticketoffice.dto.PageableDto;
import com.railway.ticketoffice.dto.TicketDto;
import com.railway.ticketoffice.exception.type.UnexpectedException;
import com.railway.ticketoffice.repository.StopRepository;
import com.railway.ticketoffice.repository.TicketRepository;
import com.railway.ticketoffice.util.PageUtil;
import com.railway.ticketoffice.validator.PassengerValidator;
import com.railway.ticketoffice.validator.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private TicketValidator ticketValidator;

    @Autowired
    private PassengerValidator passengerValidator;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private StopRepository stopRepository;


    public PageableDto<TicketDto> findPageByPassenger(Long passengerId, Integer page, Boolean isActive) {
        passengerValidator.validateExistence(passengerId);

        Page<TicketDto> ticketsDtoPage = ticketRepository.findPageByPassengerIdAndActiveStatus(
                passengerId, LocalDate.now(), isActive, PageUtil.getPageableFromPageNumber(page));
        PageUtil.checkPageBounds(page, ticketsDtoPage);
        PageableDto<TicketDto> result = new PageableDto<>(ticketsDtoPage.getContent(), ticketsDtoPage.getTotalPages());

        MDC.put("passengerId", passengerId.toString());
        LOGGER.info("Tickets active({}) page#{} request - found {}", isActive, page, ticketsDtoPage.getContent().size());
        return result;
    }

    public Ticket save(Ticket ticket) {
        ticketValidator.validate(ticket);
        ticket.setPrice(countTicketPrice(
                ticket.getTrainCoach().getTrain().getId(),
                ticket.getTrainCoach().getId(),
                ticket.getDepartureStation().getId(),
                ticket.getDestinationStation().getId()));

        Ticket result;
        try {
            result = ticketRepository.save(ticket);
        } catch (DataIntegrityViolationException exception) {
            throw new UnexpectedException("Unexpected error while saving ticket");
        }

        MDC.put("passengerId", ticket.getPassenger().getId().toString());
        LOGGER.info("Request for saving ticket for place#{} at coach#{} - {}", ticket.getPlace(),
                ticket.getTrainCoach().getId(), result.getId() != 0);
        return result;
    }

    public Integer countTicketPrice(Long trainId, Long trainCoachId,
                                    Long departureStationId, Long destinationStationId) throws IllegalArgumentException {
        Integer result = stopRepository.countPriceByDirectionAndTrainCoachId(trainId, trainCoachId,
                departureStationId, destinationStationId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected error while counting ticket price"));
        LOGGER.info("Request for ticket price for train#{} in coach#{} from station#{} - to station#{} - price is {}",
                trainId, trainCoachId, departureStationId, destinationStationId, result);
        return result;
    }
}
