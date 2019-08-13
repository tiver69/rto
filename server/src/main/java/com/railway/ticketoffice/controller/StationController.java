package com.railway.ticketoffice.controller;

import com.railway.ticketoffice.domain.Station;
import com.railway.ticketoffice.dto.StationSelectOptionDto;
import com.railway.ticketoffice.service.StationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping("/select")
    public List<StationSelectOptionDto> findAllStationForSelect(){
        LOG.info("Stations request for select");
        return stationService.findAllForSelect();
    }

}
