package com.railway.ticketoffice.service;

import com.railway.ticketoffice.dto.TrainInfoDto;
import com.railway.ticketoffice.repository.StationRepository;
import com.railway.ticketoffice.repository.StopRepository;
import com.railway.ticketoffice.util.DateTimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrainService {

    private static Logger LOG = Logger.getLogger(TrainService.class);

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private StationService stationService;

    public List<TrainInfoDto> findAllTrainsInDirectionAtDate(
            Long departureStation,
            Long destinationStation,
            String departureDate) throws IllegalArgumentException {
        if (!stationService.checkIfExistById(departureStation) || !stationService.checkIfExistById(departureStation))
            throw new IllegalArgumentException();

        LocalDate date = DateTimeUtil.parseString(departureDate);

        //TO_DO: filter trains by frequency and requested date
        return stopRepository.findTrainByDirection(stationRepository.findById(departureStation).get(),
                stationRepository.findById(destinationStation).get());
    }

}
