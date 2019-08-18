package com.railway.ticketoffice.service;

import com.railway.ticketoffice.dto.CoachTypeInfoDto;
import com.railway.ticketoffice.dto.request.coach.CoachInfoDto;
import com.railway.ticketoffice.repository.TicketRepository;
import com.railway.ticketoffice.repository.TrainCoachRepository;
import com.railway.ticketoffice.util.DateTimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CoachService {

    private static Logger LOG = Logger.getLogger(CoachService.class);

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TrainCoachRepository trainCoachRepository;

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
        return coachTypeList;
    }

    public List<CoachInfoDto> findAllCoachesInfoByTrainIdAndDepartureDate(Long trainId, String departureDate) {
        LocalDate date = DateTimeUtil.parseString(departureDate);
        List<CoachInfoDto> coachesInfo = trainCoachRepository.findAllCoachesByTrainId(trainId);
        List<CoachInfoDto> coachTicketsInfo = ticketRepository.findAllBookedPlacesInCoachByTrainIdAndDepartureDate(trainId, date);

        Map<Integer, List<Integer>> bookedPlaceNumberMap = coachTicketsInfo.stream()
                .collect(Collectors.groupingBy(
                        CoachInfoDto::getNumber,
                        Collectors.mapping(CoachInfoDto::getTotalPlaces, Collectors.toList())));
        coachesInfo.forEach(coach -> {
            coach.setBookedPlaces(
                    bookedPlaceNumberMap.get(coach.getNumber()) == null ? Collections.emptyList() : bookedPlaceNumberMap.get(coach.getNumber()));
            coach.setAvailablePlaces(coach.getTotalPlaces() - coach.getBookedPlaces().size());
        });
        return coachesInfo;
    }
}
