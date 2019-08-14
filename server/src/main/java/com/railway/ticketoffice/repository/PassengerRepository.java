package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Passenger;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends CrudRepository<Passenger, Long> {

    List<Passenger> findAll();

    Optional<Passenger> findById(Long id);
}
