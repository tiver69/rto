package com.railway.ticketoffice.service;

import com.railway.ticketoffice.dto.PassengerDto;
import com.railway.ticketoffice.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public boolean checkIfExistById(Long id) {
        return passengerRepository.findById(id).isPresent();
    }

    public List<PassengerDto> findAllForManaging() {
        return passengerRepository.findAllPassengersInfo();
    }
}
