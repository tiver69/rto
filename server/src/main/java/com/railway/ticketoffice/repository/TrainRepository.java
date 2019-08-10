package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Train;
import org.springframework.data.repository.CrudRepository;

public interface TrainRepository extends CrudRepository<Train, Long> {

    Iterable<Train> findAll();
}
