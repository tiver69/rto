package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.dto.PageableDto;
import com.railway.ticketoffice.dto.PassengerDto;
import com.railway.ticketoffice.exception.type.UnexpectedException;
import com.railway.ticketoffice.repository.PassengerRepository;
import com.railway.ticketoffice.util.PageUtil;
import com.railway.ticketoffice.validator.PassengerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PassengerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerService.class);

    @Autowired
    private PassengerValidator passengerValidator;

    @Autowired
    private PassengerRepository passengerRepository;

    public PageableDto<PassengerDto> findPageForManaging(Integer page) {
        Page<PassengerDto> passengerDtoPage = passengerRepository.findPagePassengersInfo(
                PageUtil.getPageableFromPageNumber(page));
        PageUtil.checkPageBounds(page, passengerDtoPage);
        PageableDto<PassengerDto> result =
                new PageableDto<>(passengerDtoPage.getContent(), passengerDtoPage.getTotalPages());

        LOGGER.info("Passengers request for manage page#{} - found {}", page, passengerDtoPage.getContent().size());
        return result;
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
