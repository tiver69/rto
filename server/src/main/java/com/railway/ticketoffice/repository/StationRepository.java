package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Station;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StationRepository extends CrudRepository<Station, Long> {

    List<Station> findAll();

}
