package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.dto.PassengerDto;
import com.railway.ticketoffice.exception.type.UnexpectedException;
import com.railway.ticketoffice.repository.PassengerRepository;
import com.railway.ticketoffice.util.PageUtil;
import com.railway.ticketoffice.validator.PassengerValidator;
import com.railway.ticketoffice.validator.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PassengerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerService.class);

    @Autowired
    private PassengerValidator passengerValidator;

    @Autowired
    private PassengerRepository passengerRepository;

    public List<PassengerDto> findPageForManaging(Integer page) {
        Page<PassengerDto> result = passengerRepository.findPagePassengersInfo(
                PageUtil.getPageableFromPageNumber(page));
        PageUtil.checkPageBounds(page, result);

        LOGGER.info("Passengers request for manage page#{} - found {}", page, result.getContent().size());
        return result.getContent();
    }

    public Integer countPageForManaging() {
        Pageable pageable = PageUtil.getPageableFromPageNumber(0);
        return passengerRepository.findPagePassengersInfo(pageable).getTotalPages();
    }

    @Transactional
    public Boolean updatePassenger(Passenger passenger) {
        passengerValidator.validate(passenger);
        Boolean result = passengerRepository.update(passenger.getId(), passenger.getFirstName(), passenger.getLastName(), passenger.getLogin())
                .equals(1);
        LOGGER.info("Request for update passenger#{} - success {}", passenger.getId(), result);
        return result;
    }

    @Transactional
    public Boolean removePassenger(Long passengerId) {
        passengerValidator.validateExistence(passengerId);
        boolean result = passengerRepository.removeById(passengerId).equals(1);
        if (!result) throw new UnexpectedException("Unexpected error while removing passenger");

        LOGGER.info("Request for deleting passenger#{} - success", passengerId);
        return true;
    }
}
