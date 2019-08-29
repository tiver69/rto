package com.railway.ticketoffice.controller;

import com.railway.ticketoffice.domain.Ticket;
import com.railway.ticketoffice.dto.TicketDto;
import com.railway.ticketoffice.service.TicketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/ticket")
public class TicketController {

    private static Logger LOG = Logger.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<?> findAllByPassenger(@RequestParam("passengerId") Long passengerId) {
        LOG.info("Tickets request for passenger#" + passengerId);

        try {
            List<TicketDto> response = ticketService.findAllByPassenger(passengerId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            LOG.error("Tickets request for passenger#" + passengerId + " - passenger not found!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/page", produces = "application/json")
    public ResponseEntity<?> findPageByPassenger(@RequestParam("passengerId") Long passengerId, @RequestParam("page") Integer page, @RequestParam("isActive") Boolean isActive) {
        LOG.info("Tickets page# " + page + " request for passenger#" + passengerId);

        try {
            List<TicketDto> response = ticketService.findPageByPassenger(passengerId, page, isActive);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            LOG.error("Tickets request for passenger#" + passengerId + " - passenger not found!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveNewTicket(@RequestBody Ticket ticket) {
        LOG.info("Adding new ticket - " + ticket);
        Ticket newTicket = ticketService.save(ticket);
        return new ResponseEntity<Ticket>(newTicket, HttpStatus.CREATED);
    }

    @GetMapping(value = "/price", produces = "application/json")
    public ResponseEntity<?> countTicketPrice(@RequestParam("trainId") Long trainId,
                                              @RequestParam("trainCoachId") Long trainCoachId,
                                              @RequestParam("departureStationId") Long departureStationId,
                                              @RequestParam("destinationStationId") Long destinationStationId) {
        LOG.info(String.format("Request for ticket price for train#%d in coach#%d from station#%d - to station#%d",
                trainId, trainCoachId, departureStationId, destinationStationId));
        try {
            Integer response = ticketService.countTicketPrice(trainId, trainCoachId, departureStationId, destinationStationId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            LOG.error("Request for ticket price - wrong arguments!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
