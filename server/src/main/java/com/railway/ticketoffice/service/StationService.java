package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.Station;
import com.railway.ticketoffice.dto.StationSelectDto;
import com.railway.ticketoffice.repository.StationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StationService.class);

    @Autowired
    private StationRepository stationRepository;

    public List<Station> findAll() {
        List<Station> stations = stationRepository.findAll();
        LOGGER.info("All stations request found - {}", stations.size());
        return stations;
    }

    public List<StationSelectDto> findAllForSelect() {
        List<StationSelectDto> stationDtoList = stationRepository.findAllStationSelect();
        LOGGER.info("Stations request for select - found {}", stationDtoList.size());
        return stationDtoList;
    }
}
