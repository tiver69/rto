package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.WeekDay;
import com.railway.ticketoffice.dto.request.train.TrainInfoDto;
import com.railway.ticketoffice.repository.StationRepository;
import com.railway.ticketoffice.repository.StopRepository;
import com.railway.ticketoffice.repository.TrainCoachRepository;
import com.railway.ticketoffice.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
public class TrainService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainService.class);

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private StopService stopService;

    @Autowired
    private CoachService coachService;

    @Transactional
    public List<TrainInfoDto> findAllTrainsInDirectionAtDate(
            Long departureStation,
            Long destinationStation,
            String departureDate) throws IllegalArgumentException {
        LocalDate date = DateTimeUtil.parseString(departureDate);

        List<TrainInfoDto> trainList =
                stopRepository.findAllTrainsByDirectionAndWeekDay(departureStation, destinationStation,
                        WeekDay.valueOf(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                                .toUpperCase()));

        trainList.forEach(train -> {
            train.setDuration(
                    DateTimeUtil.formatDuration(
                            stopService.countTrainDirectionDurationInMinutes(train.getId(), departureStation, destinationStation)));
            train.setCoachTypeInfoList(
                    coachService.findAllCoachTypesInfoByTrainIdAndDepartureDate(train.getId(), date));
        });

        LOGGER.info("Train request for {}, departureStation#{} - arrivalStation#{} - found {}",
                departureDate, departureStation, destinationStation, trainList.size());
        return trainList;
    }
}
