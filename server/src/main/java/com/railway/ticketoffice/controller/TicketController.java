package com.railway.ticketoffice.controller;

import com.railway.ticketoffice.domain.Ticket;
import com.railway.ticketoffice.dto.TicketDto;
import com.railway.ticketoffice.repository.TicketRepository;
import com.railway.ticketoffice.service.TicketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/ticket")
public class TicketController {

    private static Logger LOG = Logger.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;

    @GetMapping("/{passengerId}")
    public List<TicketDto> findAllByPassenger(@PathVariable Long passengerId) {
        LOG.info("Tickets request for passenger#"+passengerId);
        return ticketService.findAllByPassenger(passengerId);
    }
}
