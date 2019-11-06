package com.railway.ticketoffice.service;

import com.railway.ticketoffice.dto.request.coach.CoachInfoDto;
import com.railway.ticketoffice.dto.request.train.CoachTypeInfoDto;
import com.railway.ticketoffice.exception.type.DataNotFoundException;
import com.railway.ticketoffice.repository.TicketRepository;
import com.railway.ticketoffice.repository.TrainCoachRepository;
import com.railway.ticketoffice.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CoachService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoachService.class);

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TrainCoachRepository trainCoachRepository;

    @Transactional
    public List<CoachTypeInfoDto> findAllCoachTypesInfoByTrainIdAndDepartureDate(Long trainId, LocalDate departureDate) {
        List<CoachTypeInfoDto> coachTypeList = trainCoachRepository.countAllCoachTypesByTrainId(trainId);
        List<CoachTypeInfoDto> bookedPlacesList =
                ticketRepository.countBookedPlacesInCoachTypeByTrainIdAndDepartureDate(trainId, departureDate);

        Map<String, Long> bookedPlaceMap =
                bookedPlacesList.stream().collect(
                        Collectors.toMap(
                                CoachTypeInfoDto::getCoachTypeName,
                                CoachTypeInfoDto::getAvailablePlaces));
        coachTypeList.forEach(coachType -> {
            Long availablePlaces =
                    bookedPlaceMap.get(coachType.getCoachTypeName()) == null ?
                            0L :
                            bookedPlaceMap.get(coachType.getCoachTypeName());
            coachType.setAvailablePlaces(coachType.getTotalPlaces() - availablePlaces);
        });

        LOGGER.info("Coach types request for train#{} at {} - found {}", trainId, departureDate, coachTypeList.size());
        return coachTypeList;
    }

    @Transactional
    public CoachInfoDto findCoachInfoByTrainIdDepartureDateAndCoachNumber
            (Long trainId, String departureDate, Integer coachNumber) throws DataNotFoundException {
        LocalDate date = DateTimeUtil.parseString(departureDate);
        CoachInfoDto coach =
                trainCoachRepository.findCoachByNumberAndTrainId(trainId, coachNumber)
                        .orElseThrow(() -> new DataNotFoundException(
                                String.format("Coach #%d doesn't exist in this train!", coachNumber)));
        List<Integer> coachBookedPlace =
                ticketRepository.findAllBookedPlacesByCoachNumberAndTrainIdAndDepartureDate(trainId, date, coachNumber);

        coach.setBookedPlaces(coachBookedPlace);
        coach.setAvailablePlaces(coach.getTotalPlaces() - coach.getBookedPlaces().size());

        LOGGER.info("Coach#{} request for train#{} at {} - found {} available places",
                coachNumber, trainId, departureDate, coach.getAvailablePlaces());
        return coach;
    }
}
