package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.dto.PassengerDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends CrudRepository<Passenger, Long> {

    List<Passenger> findAll();

    Optional<Passenger> findById(Long id);

    @Query("SELECT new com.railway.ticketoffice.dto.PassengerDto" +
            "(p.id, p.lastName, p.firstName, p.login, count(t.id), max(t.departureDate)) " +
            "FROM Passenger p Join Ticket t On t.passenger.id = p.id " +
            "Group By p")
    List<PassengerDto> findAllPassengersInfo();
}
