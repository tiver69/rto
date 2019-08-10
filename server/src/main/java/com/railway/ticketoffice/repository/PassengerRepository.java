package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Passenger;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PassengerRepository extends CrudRepository<Passenger, Long> {

    List<Passenger> findAll();
}
