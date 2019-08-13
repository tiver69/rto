package com.railway.ticketoffice.controller;

import com.railway.ticketoffice.domain.Ticket;
import com.railway.ticketoffice.dto.TicketDto;
import com.railway.ticketoffice.repository.TicketRepository;
import com.railway.ticketoffice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/ticket")
public class TicketController {


    @Autowired
    private TicketService ticketService;

    @GetMapping("/all")
    public List<Ticket> findAllTickets(){
        return ticketService.findAll();
    }

    @GetMapping("/{passengerId}")
    public List<TicketDto> findAllByPassenger(@PathVariable Long passengerId) {
        return ticketService.findAllByPassenger(passengerId);
    }
}
