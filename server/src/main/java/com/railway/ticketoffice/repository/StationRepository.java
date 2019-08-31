package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Station;
import com.railway.ticketoffice.dto.StationSelectDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StationRepository extends CrudRepository<Station, Long> {

    List<Station> findAll();

    Optional<Station> findById(Long id);

    @Query("SELECT new com.railway.ticketoffice.dto.StationSelectDto(s.id, s.name) " +
            "FROM Station s")
    List<StationSelectDto> findAllStationSelect();
}
