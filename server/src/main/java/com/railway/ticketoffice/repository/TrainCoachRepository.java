package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.TrainCoach;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrainCoachRepository extends CrudRepository<TrainCoach, Long> {

    List<TrainCoach> findAll();

}
