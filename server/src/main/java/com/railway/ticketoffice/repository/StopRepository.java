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

    @Query("SELECT new com.railway.ticketoffice.dto.TrainInfoDto(departure.train, departure.departure, arrival.arrival) " +
            "FROM Stop departure, Stop arrival " +
            "WHERE departure.station = :departureStation " +
            "AND arrival.station = :destinationStation " +
            "AND departure.train = arrival.train " +
            "AND departure.order < arrival.order")
    List<TrainInfoDto> findTrainByDirection(@Param("departureStation") Station departureStation,
                             @Param("destinationStation") Station destinationStation);

}
