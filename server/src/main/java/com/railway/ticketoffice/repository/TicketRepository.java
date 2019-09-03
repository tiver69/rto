package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Ticket;
import com.railway.ticketoffice.dto.TicketDto;
import com.railway.ticketoffice.dto.request.train.CoachTypeInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    List<Ticket> findAll();

    @Query("SELECT new com.railway.ticketoffice.dto.TicketDto(t.id, t.departureStation.name, t.destinationStation.name, t.departureDate, " +
            "s.departure, t.arrivalDate, s1.arrival, t.trainCoach.train.id, t.trainCoach.number, t.place, t.price) " +
            "FROM Ticket t " +
            "JOIN Stop s ON t.trainCoach.train.id = s.train.id AND t.departureStation.id = s.station.id " +
            "JOIN Stop s1 ON t.trainCoach.train.id = s1.train.id AND t.destinationStation.id = s1.station.id " +
            "WHERE t.passenger.id = :passengerId AND " +
            "((t.departureDate >= :nowDate AND :isActive is true) " +
            "OR " +
            "(t.departureDate < :nowDate AND :isActive IS NOT true))")
    Page<TicketDto> findPageByPassengerIdAndActiveStatus(@Param("passengerId") Long passengerId,
                                                         @Param("nowDate") LocalDate nowDate,
                                                         @Param("isActive") Boolean isActive,
                                                         Pageable pageable);

    @Query("SELECT new com.railway.ticketoffice.dto.request.train.CoachTypeInfoDto(t.trainCoach.coachType.name, count(*)) " +
            "FROM Ticket t " +
            "WHERE t.departureDate = :departureDate " +
            "AND t.trainCoach.train.id = :trainId " +
            "group by t.trainCoach.coachType ")
    List<CoachTypeInfoDto> countBookedPlacesInCoachTypeByTrainIdAndDepartureDate(@Param("trainId") Long trainId,
                                                                                 @Param("departureDate") LocalDate departureDate);

    @Query("SELECT t.place " +
            "FROM Ticket t " +
            "WHERE t.trainCoach.train.id = :trainId " +
            "AND t.departureDate = :departureDate " +
            "AND t.trainCoach.number = :coachNumber")
    List<Integer> findAllBookedPlacesByCoachNumberAndTrainIdAndDepartureDate(@Param("trainId") Long trainId,
                                                                             @Param("departureDate") LocalDate departureDate,
                                                                             @Param("coachNumber") Integer coachNumber);
}
