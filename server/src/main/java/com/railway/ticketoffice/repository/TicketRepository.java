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
            "s.departure, s1.arrival, t.trainCoach.train.id, t.trainCoach.number, t.place, t.price) " +
            "FROM Ticket t " +
            "JOIN Stop s ON t.trainCoach.train.id = s.train.id AND t.departureStation.id = s.station.id " +
            "JOIN Stop s1 ON t.trainCoach.train.id = s1.train.id AND t.destinationStation.id = s1.station.id " +
            "WHERE t.passenger.id = :passengerId AND t.departureDate >= :nowDate")
    Page<TicketDto> findActivePageByPassengerId(@Param("passengerId") Long passengerId,
                                                @Param("nowDate") LocalDate nowDate,
                                                @Param("pageable") Pageable pageable);

    @Query("SELECT new com.railway.ticketoffice.dto.TicketDto(t.id, t.departureStation.name, t.destinationStation.name, t.departureDate, " +
            "s.departure, s1.arrival, t.trainCoach.train.id, t.trainCoach.number, t.place, t.price) " +
            "FROM Ticket t " +
            "JOIN Stop s ON t.trainCoach.train.id = s.train.id AND t.departureStation.id = s.station.id " +
            "JOIN Stop s1 ON t.trainCoach.train.id = s1.train.id AND t.destinationStation.id = s1.station.id " +
            "WHERE t.passenger.id = :passengerId AND t.departureDate < :nowDate")
    Page<TicketDto> findHistoryPageByPassengerId(@Param("passengerId") Long passengerId,
                                                 @Param("nowDate") LocalDate nowDate,
                                                 @Param("pageable") Pageable pageable);

    @Query("SELECT new com.railway.ticketoffice.dto.request.train.CoachTypeInfoDto(tc.coachType.name, count(*)) " +
            "FROM Ticket t JOIN TrainCoach tc " +
            "ON t.trainCoach.id = tc.id " +
            "WHERE t.departureDate = :departureDate " +
            "AND tc.train.id = :trainId " +
            "group by tc.coachType ")
    List<CoachTypeInfoDto> countBookedPlacesInCoachTypeByTrainIdAndDepartureDate(@Param("trainId") Long trainId,
                                                                                 @Param("departureDate") LocalDate departureDate);

    @Query("SELECT t.place " +
            "FROM TrainCoach tc JOIN Ticket t " +
            "ON tc.id=t.trainCoach.id " +
            "WHERE tc.train.id = :trainId AND t.departureDate = :departureDate AND tc.number = :coachNumber")
    List<Integer> findAllBookedPlacesByCoachNumberAndTrainIdAndDepartureDate(@Param("trainId") Long trainId,
                                                                             @Param("departureDate") LocalDate departureDate,
                                                                             @Param("coachNumber") Integer coachNumber);
}
