package com.railway.ticketoffice.service;

import com.railway.ticketoffice.repository.PassengerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public boolean checkIfExistById(Long id) {
        return passengerRepository.findById(id).isPresent();
    }

}
