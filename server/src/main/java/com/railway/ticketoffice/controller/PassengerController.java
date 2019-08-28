package com.railway.ticketoffice.controller;

import com.railway.ticketoffice.dto.PassengerDto;
import com.railway.ticketoffice.service.PassengerService;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
