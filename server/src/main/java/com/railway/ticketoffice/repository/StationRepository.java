package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Station;
import org.springframework.data.repository.CrudRepository;

public interface StationRepository extends CrudRepository<Station, Long> {

    Iterable<Station> findAll();

}
