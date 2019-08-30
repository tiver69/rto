package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.TrainCoach;
import com.railway.ticketoffice.dto.request.train.TrainInfoDto;
import com.railway.ticketoffice.repository.StationRepository;
import com.railway.ticketoffice.repository.StopRepository;
import com.railway.ticketoffice.repository.TrainCoachRepository;
import com.railway.ticketoffice.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrainService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainService.class);

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private TrainCoachRepository trainCoachRepository;

    @Autowired
    private StationService stationService;

    @Autowired
    private CoachService coachService;

    public List<TrainInfoDto> findAllTrainsInDirectionAtDate(
            Long departureStation,
            Long destinationStation,
            String departureDate) throws IllegalArgumentException {
        LocalDate date = DateTimeUtil.parseString(departureDate);
        //TO_DO: filter trains by frequency and requested date
        //TO_DO: add arrival date for cases when more than 24 hours

        List<TrainInfoDto> trainList =
                stopRepository.findAllTrainsByDirection(departureStation, destinationStation);

        trainList.forEach(train -> {
            train.setFirstStationName(stopRepository.findByTrainIdAndOrder(train.getId(), 0).orElseThrow(IllegalArgumentException::new).getStation().getName());
            train.setLastStationName(stopRepository.findFirstByTrainIdOrderByOrderDesc(train.getId()).orElseThrow(IllegalArgumentException::new).getStation().getName());
            train.setCoachNumber(trainCoachRepository.findFirstByTrainIdOrderByNumberDesc(train.getId())
                    .map(TrainCoach::getNumber)
                    .orElseThrow(IllegalAccessError::new));
            train.setDuration(DateTimeUtil.getDuration(train.getDepartureTime(), train.getArrivalTime()));
            train.setCoachTypeInfoList(
                    coachService.findAllCoachTypesInfoByTrainIdAndDepartureDate(train.getId(), date));
        });

        LOGGER.info("Train request for {}, departureStation#{} - arrivalStation#{} - found {}",
                departureDate, departureStation, destinationStation, trainList.size());
        return trainList;
    }

}
