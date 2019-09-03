package com.railway.ticketoffice.controller;

import com.railway.ticketoffice.dto.request.coach.CoachInfoDto;
import com.railway.ticketoffice.service.CoachService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/coach")
public class CoachController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoachController.class);

    @Autowired
    private CoachService coachService;

    @GetMapping("/search")
    public ResponseEntity<?> findAllCoachesByTrainIdAndDepartureDate(
            @RequestParam("trainId") Long trainId,
            @RequestParam("departureDate") String departureDate,
            @RequestParam("coachNumber") Integer coachNumber) {
        LOGGER.debug("Coach#{} request for train#{} at {}", coachNumber, trainId, departureDate);

        CoachInfoDto response =
                coachService.findCoachInfoByTrainIdDepartureDateAndCoachNumber(trainId, departureDate, coachNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
