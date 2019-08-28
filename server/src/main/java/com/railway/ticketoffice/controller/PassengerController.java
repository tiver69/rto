package com.railway.ticketoffice.controller;

import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.dto.PassengerDto;
import com.railway.ticketoffice.service.PassengerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/passenger")
public class PassengerController {

    private static Logger LOG = Logger.getLogger(PassengerController.class);

    @Autowired
    private PassengerService passengerService;

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<?> findAllPassengersForManaging(){
        LOG.info("Passengers request for manage page");

        List<PassengerDto> response = passengerService.findAllForManaging();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value="/update", produces = "application/json")
    public ResponseEntity<?> updatePassenger(@RequestBody Passenger passenger){
        LOG.info("Request for update passenger#"+ passenger.getId());

        Boolean response = passengerService.updatePassenger(passenger);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
