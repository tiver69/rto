package com.railway.ticketoffice.controller;

import com.railway.ticketoffice.dto.TrainInfoDto;
import com.railway.ticketoffice.service.TrainService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/train")
public class TrainController {

    private static Logger LOG = Logger.getLogger(TrainController.class);

    @Autowired
    private TrainService trainService;

    @GetMapping("/search")
    public ResponseEntity<?> findAllByPassenger(
            @RequestParam("departureStation") Long departureStation,
            @RequestParam("destinationStation") Long destinationStation,
            @RequestParam("departureDate") String departureDate) {
        LOG.info(String.format("Train request for %s, departureStation#%d - arrivalStation#%d",
                departureDate, departureStation, destinationStation));

        List<TrainInfoDto> response =
                trainService.findAllTrainsInDirectionAtDate(departureStation, destinationStation, departureDate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
