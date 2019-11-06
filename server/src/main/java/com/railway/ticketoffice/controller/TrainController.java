package com.railway.ticketoffice.controller;

import com.railway.ticketoffice.dto.request.train.TrainInfoDto;
import com.railway.ticketoffice.service.TrainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/train")
public class TrainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainController.class);

    @Autowired
    private TrainService trainService;

    @GetMapping("/search")
    public ResponseEntity<?> findAllTrainsInDirectionAtDate(
            @RequestParam("departureStation") Long departureStation,
            @RequestParam("destinationStation") Long destinationStation,
            @RequestParam("departureDate") String departureDate) {
        LOGGER.debug("Train request for {}, departureStation#{} - arrivalStation#{}", departureDate, departureStation, destinationStation);

        List<TrainInfoDto> response =
                trainService.findAllTrainsInDirectionAtDate(departureStation, destinationStation, departureDate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

