package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Station;
import com.railway.ticketoffice.domain.Stop;
import com.railway.ticketoffice.domain.Train;
import com.railway.ticketoffice.domain.key.StopKey;
import com.railway.ticketoffice.dto.TrainInfoDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StopRepository extends CrudRepository<Stop, StopKey> {

    List<Stop> findAll();

    Optional<Stop> findByTrainIdAndStationId(Long trainId, Long StationId);

    @Query("SELECT new com.railway.ticketoffice.dto.TrainInfoDto(departure.train.id, departure.departure, destination.arrival) " +
            "FROM Stop departure, Stop destination " +
            "WHERE departure.station.id = :departureStationId " +
            "AND destination.station.id = :destinationStationId " +
            "AND departure.train = destination.train " +
            "AND departure.order < destination.order")
    List<TrainInfoDto> findAllTrainsByDirection(@Param("departureStationId") Long departureStationId,
                                                @Param("destinationStationId") Long destinationStationId);

    Optional<Stop> findByTrainIdAndOrder(Long trainId, Integer order);
    Optional<Stop> findFirstByTrainIdOrderByOrderDesc(Long trainId);
}
