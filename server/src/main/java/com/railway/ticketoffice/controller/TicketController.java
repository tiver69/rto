package com.railway.ticketoffice.controller;

import com.railway.ticketoffice.domain.Ticket;
import com.railway.ticketoffice.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/ticket")
public class TicketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;

    @GetMapping(value = "/page", produces = "application/json")
    public ResponseEntity<?> findPageByPassenger(@RequestParam("passengerId") Long passengerId,
                                                 @RequestParam("page") Integer page,
                                                 @RequestParam("isActive") Boolean isActive) {
        MDC.put("passengerId", passengerId.toString());
        LOGGER.debug("Tickets page#{} request", page);

        List response = ticketService.findPageByPassenger(passengerId, page, isActive);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/page/count", produces = "application/json")
    public ResponseEntity<Integer> countPageByPassenger(@RequestParam("passengerId") Long passengerId,
                                                        @RequestParam("isActive") Boolean isActive) {
        Integer response = ticketService.countPageByPassenger(passengerId, isActive);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveNewTicket(@RequestBody Ticket ticket) {
        MDC.put("passengerId", ticket.getPassenger().getId().toString());
        LOGGER.debug("Adding new ticket - {}", ticket);
        Ticket newTicket = ticketService.save(ticket);
        return new ResponseEntity<>(newTicket, HttpStatus.CREATED);
    }

    @GetMapping(value = "/price", produces = "application/json")
    public ResponseEntity<?> countTicketPrice(@RequestParam("trainId") Long trainId,
                                              @RequestParam("trainCoachId") Long trainCoachId,
                                              @RequestParam("departureStationId") Long departureStationId,
                                              @RequestParam("destinationStationId") Long destinationStationId) {
        LOGGER.debug("Request for ticket price for train#{} in coach#{} from station#{} - to station#{}",
                trainId, trainCoachId, departureStationId, destinationStationId);
        Integer response = ticketService.countTicketPrice(trainId, trainCoachId, departureStationId, destinationStationId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
