package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.Station;
import com.railway.ticketoffice.dto.StationSelectDto;
import com.railway.ticketoffice.repository.StationRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationService {

    private static Logger LOG = Logger.getLogger(StationService.class);

    @Autowired
    private StationRepository stationRepository;

    public List<Station> findAll() {
        List<Station> stations = stationRepository.findAll();
        LOG.info("All stations request found - " + stations.size());
        return stations;
    }

    public List<StationSelectDto> findAllForSelect() {
        List<Station> stations = stationRepository.findAll();
        List<StationSelectDto> stationDtoList = new ArrayList<>();
        stations.forEach(station -> {
            stationDtoList.add(StationSelectDto.builder()
                    .value(station.getId())
                    .label(station.getName())
                    .build());
        });

        LOG.info("Stations request for select - found " + stationDtoList.size());
        return stationDtoList;
    }
}
