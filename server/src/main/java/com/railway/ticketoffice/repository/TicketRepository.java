package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Ticket;
import com.railway.ticketoffice.dto.request.train.CoachTypeInfoDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    List<Ticket> findAll();

    List<Ticket> findAllByPassengerId(Long id);

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
