package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Stop;
import com.railway.ticketoffice.domain.key.StopKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StopRepository extends CrudRepository<Stop, StopKey> {

    List<Stop> findAll();

    Optional<Stop> findByTrainIdAndStationId(Long trainId, Long StationId);

}
