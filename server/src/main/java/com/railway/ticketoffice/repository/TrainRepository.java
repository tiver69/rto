package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Train;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrainRepository extends CrudRepository<Train, Long> {

    List<Train> findAll();
}
