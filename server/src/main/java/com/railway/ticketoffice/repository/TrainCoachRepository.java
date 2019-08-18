package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.TrainCoach;
import com.railway.ticketoffice.dto.CoachTypeInfoDto;
import com.railway.ticketoffice.dto.request.coach.CoachInfoDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainCoachRepository extends CrudRepository<TrainCoach, Long> {

    List<TrainCoach> findAll();

    @Query("SELECT new com.railway.ticketoffice.dto.CoachTypeInfoDto(tc.coachType.name, count(*), sum(tc.coachType.places)) " +
            "FROM TrainCoach tc " +
            "WHERE tc.train.id = :trainId " +
            "group by tc.coachType ")
    List<CoachTypeInfoDto> countAllCoachTypesByTrainId(@Param("trainId") Long trainId);

    @Query("SELECT new com.railway.ticketoffice.dto.request.coach.CoachInfoDto(tc.coachType.name, tc.number, tc.coachType.places) " +
            "FROM TrainCoach tc " +
            "WHERE tc.train.id = :trainId " +
            "ORDER BY tc.number")
    List <CoachInfoDto> findAllCoachesByTrainId(@Param("trainId") Long trainId);
}
