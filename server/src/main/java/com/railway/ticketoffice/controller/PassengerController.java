package com.railway.ticketoffice.controller;

import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.dto.PassengerDto;
import com.railway.ticketoffice.service.PassengerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/passenger")
public class PassengerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerController.class);

    @Autowired
    private PassengerService passengerService;

    @GetMapping(value = "/page", produces = "application/json")
    public ResponseEntity<?> findPagePassengersForManaging(@RequestParam("page") Integer page) {
        LOGGER.debug("Passengers page#{} request for manage page", page);

        List<PassengerDto> response = passengerService.findPageForManaging(page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/page/count", produces = "application/json")
    public ResponseEntity<Integer> countPagePassengersForManaging() {
        Integer response = passengerService.countPageForManaging();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/update", produces = "application/json")
    public ResponseEntity<?> updatePassenger(@RequestBody Passenger passenger) {
        LOGGER.debug("Request for update passenger#{}", passenger.getId());

        Boolean response = passengerService.updatePassenger(passenger);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{passengerId}")
    public ResponseEntity<?> removePassenger(@PathVariable Long passengerId) {
        LOGGER.debug("Request for deleting passenger#{}", passengerId);

        Boolean response = passengerService.removePassenger(passengerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
