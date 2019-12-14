package com.railway.ticketoffice.controller;

import com.railway.ticketoffice.domain.Ticket;
import com.railway.ticketoffice.dto.PageableDto;
import com.railway.ticketoffice.dto.TicketDto;
import com.railway.ticketoffice.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/ticket")
public class TicketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;

    @GetMapping(value = "/page", produces = "application/json")
    public ResponseEntity<?> findPageByPassenger(@RequestParam Integer page, @RequestParam Boolean isActive,
                                                 Principal principal) {
        MDC.put("passengerId", principal.getName());
        LOGGER.debug("Tickets page#{} request", page);

        PageableDto<TicketDto> response = ticketService.findPageByPassenger(principal.getName(), page, isActive);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveNewTicket(@RequestBody Ticket ticket, Principal principal) {
        MDC.put("passengerId", ticket.getPassenger().getId().toString());
        LOGGER.debug("Adding new ticket - {}", ticket);
        Ticket newTicket = ticketService.save(ticket, principal.getName());
        return new ResponseEntity<>(newTicket, HttpStatus.CREATED);
    }

    @GetMapping(value = "/price", produces = "application/json")
    public ResponseEntity<?> countTicketPrice(@RequestParam Long trainId,
                                              @RequestParam Long trainCoachId,
                                              @RequestParam Long departureStationId,
                                              @RequestParam Long destinationStationId) {
        LOGGER.debug("Request for ticket price for train#{} in coach#{} from station#{} - to station#{}",
                trainId, trainCoachId, departureStationId, destinationStationId);
        //TO_DO: refactor for a proper validation of this request independent from save-request
        Integer response = ticketService.countTicketPrice(trainId, trainCoachId, departureStationId, destinationStationId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
