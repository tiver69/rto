package com.railway.ticketoffice.controller;

import com.railway.ticketoffice.dto.request.coach.CoachInfoDto;
import com.railway.ticketoffice.service.CoachService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/coach")
public class CoachController {

    private static String LOG_FORMAT = "Coaches request for train#%d at %s";
    private static Logger LOG = Logger.getLogger(CoachController.class);

    @Autowired
    private CoachService coachService;


    @GetMapping("/search")
    public ResponseEntity<?> findAllCoachesByTrainIdAndDepartureDate(
            @RequestParam("trainId") Long trainId,
            @RequestParam("departureDate") String departureDate) {
        LOG.info(String.format(LOG_FORMAT, trainId, departureDate));

        try {
            List<CoachInfoDto> response =
                    coachService.findAllCoachesInfoByTrainIdAndDepartureDate(trainId, departureDate);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (DateTimeParseException e) {
            LOG.error(String.format(LOG_FORMAT, trainId, departureDate) +
                    " - bad request!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
