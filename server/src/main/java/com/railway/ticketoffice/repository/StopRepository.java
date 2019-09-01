package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Stop;
import com.railway.ticketoffice.domain.WeekDay;
import com.railway.ticketoffice.domain.key.StopKey;
import com.railway.ticketoffice.dto.request.train.TrainInfoDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface StopRepository extends CrudRepository<Stop, StopKey> {

    List<Stop> findAll();

    Optional<Stop> findByTrainIdAndStationId(Long trainId, Long StationId);

    @Query("SELECT new com.railway.ticketoffice.dto.request.train.TrainInfoDto(" +
            "departure.train.id, " +
            "(SELECT MAX(tc.number) FROM TrainCoach tc WHERE tc.train.id = departure.train.id), " +
            "(SELECT s.station.name FROM Stop s WHERE s.train.id = departure.train.id AND s.order = 0), " +
            "(SELECT s1.station.name FROM Stop s1 WHERE s1.train.id = departure.train.id AND s1.order = (SELECT MAX(s2.order) FROM Stop s2 WHERE s2.train.id = departure.train.id)), " +
            "departure.departure, destination.arrival) " +
            "FROM Stop departure, Stop destination " +
            "WHERE departure.station.id = :departureStationId " +
            "AND destination.station.id = :destinationStationId " +
            "AND departure.train = destination.train " +
            "AND departure.order < destination.order " +
            "AND :weekDay MEMBER OF departure.train.frequency")
    List<TrainInfoDto> findAllTrainsByDirectionAndWeekDay(@Param("departureStationId") Long departureStationId,
                                                          @Param("destinationStationId") Long destinationStationId,
                                                          @Param("weekDay") WeekDay weekDay);

    @Query("SELECT s FROM Stop s WHERE s.train.id = :trainId " +
            "AND s.order >= (SELECT s1.order FROM Stop s1  WHERE s1.station.id = :departureStationId AND s1.train.id = :trainId) " +
            "AND s.order <= (SELECT s2.order FROM Stop s2  WHERE s2.station.id = :destinationStationId AND s2.train.id = :trainId) " +
            "ORDER BY s.order")
    List<Stop> getAllStopsInDirectionAndTrainId(@Param("departureStationId") Long departureStationId,
                                                @Param("destinationStationId") Long destinationStationId,
                                                @Param("trainId") Long trainId);

    @Query("SELECT SUM(s.price) + " +
            "SUM(s.price) * (SELECT t.markup From Train t where t.id = :trainId) / 100 + " +
            "SUM(s.price) * (SELECT tc.coachType.markup From TrainCoach tc where tc.id = :trainCoachId) / 100 " +
            "FROM Stop s " +
            "WHERE s.order > (SELECT s1.order FROM Stop s1 WHERE s1.station.id = :departureStationId AND s1.train.id = :trainId) " +
            "AND " +
            "s.order <= (SELECT s2.order FROM Stop s2 WHERE s2.station.id = :destinationStationId AND s2.train.id = :trainId) " +
            "AND " +
            "s.train.id = :trainId")
    Optional<Integer> countPriceByDirectionAndTrainCoachId(@Param("trainId") Long trainId,
                                                           @Param("trainCoachId") Long trainCoachId,
                                                           @Param("departureStationId") Long departureStationId,
                                                           @Param("destinationStationId") Long destinationStationId);
}
