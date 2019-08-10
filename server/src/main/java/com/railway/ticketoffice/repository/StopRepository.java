package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Stop;
import com.railway.ticketoffice.domain.key.StopKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StopRepository extends CrudRepository<Stop, StopKey> {

    List<Stop> findAll();

}
