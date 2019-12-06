package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.CoachType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CoachTypeRepository extends CrudRepository<CoachType, Long> {

    List<CoachType> findAll();
}
