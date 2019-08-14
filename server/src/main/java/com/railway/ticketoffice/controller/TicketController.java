package com.railway.ticketoffice.controller;

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
            LOG.error("Tickets request for passenger#" + passengerId+" - passenger not found!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
