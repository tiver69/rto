package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    List<Ticket> findAll();

    List<Ticket> findAllByPassengerId(Long id);
}
