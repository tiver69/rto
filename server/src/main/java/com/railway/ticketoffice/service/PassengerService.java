package com.railway.ticketoffice.service;

import com.railway.ticketoffice.controller.PassengerController;
import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.dto.PassengerDto;
import com.railway.ticketoffice.repository.PassengerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PassengerService {

    private static Logger LOG = Logger.getLogger(PassengerService.class);

    @Autowired
    private PassengerRepository passengerRepository;

    public boolean checkIfExistById(Long id) {
        return passengerRepository.findById(id).isPresent();
    }

    public List<PassengerDto> findAllForManaging() {
        List<PassengerDto> result = passengerRepository.findAllPassengersInfo();

        LOG.info("Passengers request for manage page - found " + result.size());
        return result;
    }

    @Transactional
    public Boolean updatePassenger(Passenger passenger) {
//        TO_DO: validation
        return
                passengerRepository.update(passenger.getId(), passenger.getFirstName(), passenger.getLastName(), passenger.getLogin())
                        .equals(1);
    }

    @Transactional
    public Boolean removePassenger(Long passengerId) {
        return passengerRepository.removeById(passengerId).equals(1);
    }
}
