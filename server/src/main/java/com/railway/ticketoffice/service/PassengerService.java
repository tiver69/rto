package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.dto.PassengerDto;
import com.railway.ticketoffice.repository.PassengerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PassengerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerService.class);
    private static final Integer ITEMS_PER_PAGE = 5;

    @Autowired
    private PassengerRepository passengerRepository;

    public boolean checkIfExistById(Long id) {
        return passengerRepository.findById(id).isPresent();
    }

    public List<PassengerDto> findPageForManaging(Integer page) {
        Pageable pageable = PageRequest.of(page, ITEMS_PER_PAGE);
        List<PassengerDto> result = passengerRepository.findPagePassengersInfo(pageable).getContent();

        LOGGER.info("Passengers request for manage page#{} - found {}", page, result.size());
        return result;
    }

    public Integer countPageForManaging() {
        Pageable pageable = PageRequest.of(0, ITEMS_PER_PAGE);
        return passengerRepository.findPagePassengersInfo(pageable).getTotalPages();
    }

    @Transactional
    public Boolean updatePassenger(Passenger passenger) {
//        TO_DO: validation
        Boolean result = passengerRepository.update(passenger.getId(), passenger.getFirstName(), passenger.getLastName(), passenger.getLogin())
                .equals(1);
        LOGGER.info("Request for update passenger#{} - success {}", passenger.getId(), result);
        return result;

    }

    @Transactional
    public Boolean removePassenger(Long passengerId) {
        Boolean result = passengerRepository.removeById(passengerId).equals(1);
        LOGGER.info("Request for deleting passenger#{}- success {}", passengerId, result);
        return result;
    }
}
