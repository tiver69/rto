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

import static com.railway.ticketoffice.controller.TrainController.LOG_FORMAT;

@Service
public class TrainService {

    private static Logger LOG = Logger.getLogger(TrainService.class);

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private StationRepository stationRepository;

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
            train.setFirstStationName(
                    stopRepository.findByTrainIdAndOrder(train.getId(), 0)
                            .orElseThrow(IllegalArgumentException::new)
                            .getStation().getName());
            train.setLastStationName(
                    stopRepository.findFirstByTrainIdOrderByOrderDesc(train.getId())
                            .orElseThrow(IllegalArgumentException::new)
                            .getStation().getName());
            train.setDuration(DateTimeUtil.getDuration(train.getDepartureTime(), train.getArrivalTime()));
            train.setCoachTypeInfoList(
                    coachService.findAllCoachTypesInfoByTrainIdAndDepartureDate(train.getId(), date));
        });

        LOG.info(String.format(LOG_FORMAT, departureDate, departureStation, destinationStation) + " - found " + trainList.size());
        return trainList;
    }

}
