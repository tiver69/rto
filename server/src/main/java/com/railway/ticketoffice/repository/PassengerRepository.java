package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.dto.PassengerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends CrudRepository<Passenger, Long> {

    List<Passenger> findAll();

    Optional<Passenger> findById(Long id);

    Optional<Passenger> findByLogin(String login);

    @Query("SELECT new com.railway.ticketoffice.dto.PassengerDto" +
            "(p.id, p.firstName, p.lastName, p.login, count(t.id), max(t.departureDate)) " +
            "FROM Passenger p LEFT JOIN Ticket t On t.passenger.id = p.id " +
            "Group By p")
    Page<PassengerDto> findPagePassengersInfo(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Passenger p SET p.firstName = :firstName, p.lastName = :lastName, p.login = :login WHERE p.id = :id")
    Integer update(@Param("id") Long id,
                   @Param("firstName") String firstName,
                   @Param("lastName") String lastName,
                   @Param("login") String login);

    Integer removeById(Long passengerId);
}
