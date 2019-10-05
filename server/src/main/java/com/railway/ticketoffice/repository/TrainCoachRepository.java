package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.TrainCoach;
import com.railway.ticketoffice.dto.request.coach.CoachInfoDto;
import com.railway.ticketoffice.dto.request.train.CoachTypeInfoDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrainCoachRepository extends CrudRepository<TrainCoach, Long> {

    List<TrainCoach> findAll();

    Optional<TrainCoach> findById(Long id);

    Optional<TrainCoach> findByIdAndTrainId(Long trainCoachId, Long trainId);

    @Query("SELECT new com.railway.ticketoffice.dto.request.train.CoachTypeInfoDto(tc.coachType.name, count(*), sum(tc.coachType.places)) " +
            "FROM TrainCoach tc " +
            "WHERE tc.train.id = :trainId " +
            "group by tc.coachType ")
    List<CoachTypeInfoDto> countAllCoachTypesByTrainId(@Param("trainId") Long trainId);

    @Query("SELECT new com.railway.ticketoffice.dto.request.coach.CoachInfoDto(tc.id ,tc.coachType.id, tc.coachType.name, tc.number, tc.coachType.places) " +
            "FROM TrainCoach tc " +
            "WHERE tc.train.id = :trainId " +
            "AND tc.number = :coachNumber")
    Optional<CoachInfoDto> findCoachByNumberAndTrainId(@Param("trainId") Long trainId, @Param("coachNumber") Integer coachNumber);

    Optional<TrainCoach> findFirstByTrainIdOrderByNumberDesc(Long trainId);
}
