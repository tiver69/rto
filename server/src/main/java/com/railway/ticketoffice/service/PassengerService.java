package com.railway.ticketoffice.service;

import com.railway.ticketoffice.controller.PassengerController;
import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.dto.PassengerDto;
import com.railway.ticketoffice.repository.PassengerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PassengerService {

    private static Logger LOG = Logger.getLogger(PassengerService.class);
    private static Integer ITEMS_PER_PAGE = 5;

    @Autowired
    private PassengerRepository passengerRepository;

    public boolean checkIfExistById(Long id) {
        return passengerRepository.findById(id).isPresent();
    }

    public List<PassengerDto> findPageForManaging(Integer page) {
        Pageable pageable = PageRequest.of(page, ITEMS_PER_PAGE);
        List<PassengerDto> result = passengerRepository.findPagePassengersInfo(pageable).getContent();

        LOG.info("Passengers request for manage page - found " + result.size());
        return result;
    }

    public Integer countPageForManaging() {
        Pageable pageable = PageRequest.of(0, ITEMS_PER_PAGE);
        return passengerRepository.findPagePassengersInfo(pageable).getTotalPages();
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
