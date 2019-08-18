package com.railway.ticketoffice.controller;

import com.railway.ticketoffice.dto.TrainInfoDto;
import com.railway.ticketoffice.repository.TrainCoachRepository;
import com.railway.ticketoffice.service.CoachService;
import com.railway.ticketoffice.service.TrainService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/train")
public class TrainController {

    public static String LOG_FORMAT = "Train request for %s, departureStation#%d - arrivalStation#%d";
    private static Logger LOG = Logger.getLogger(TrainController.class);

    @Autowired
    private TrainService trainService;

    @GetMapping("/search")
    public ResponseEntity<?> findAllTrainsInDirectionAtDate(
            @RequestParam("departureStation") Long departureStation,
            @RequestParam("destinationStation") Long destinationStation,
            @RequestParam("departureDate") String departureDate) {
        LOG.info(String.format(LOG_FORMAT, departureDate, departureStation, destinationStation));

        try {
            List<TrainInfoDto> response =
                    trainService.findAllTrainsInDirectionAtDate(departureStation, destinationStation, departureDate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException | DateTimeParseException e) {
            LOG.error(String.format(LOG_FORMAT, departureDate, departureStation, destinationStation) +
                    " - bad request!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

