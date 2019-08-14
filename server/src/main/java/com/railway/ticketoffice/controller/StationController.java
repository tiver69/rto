package com.railway.ticketoffice.controller;

import com.railway.ticketoffice.dto.StationSelectDto;
import com.railway.ticketoffice.service.StationService;
import org.apache.log4j.Logger;
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
@RequestMapping("/api/station")
public class StationController {

    private static Logger LOG = Logger.getLogger(StationController.class);

    @Autowired
    private StationService stationService;


    @GetMapping(value = "/select", produces = "application/json")
    public ResponseEntity<?> findAllStationForSelect(){
        LOG.info("Stations request for select");

        List<StationSelectDto> response = stationService.findAllForSelect();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
